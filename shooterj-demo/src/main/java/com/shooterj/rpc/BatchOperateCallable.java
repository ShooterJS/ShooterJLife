package com.shooterj.rpc;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/10/18
 *
 * https://segmentfault.com/a/1190000040768959?sort=newest
 *
 * 通常我们提交给线程池后直接遍历Future并等待获取结果就好了。但是这里我们用CountDownLatch来做更加统一的超时管理。可以看一下BatchOperateCallable的实现
 * 无论调用时成功还是异常，我们都会在结束后将计数器减一。当计数器被减到0时，则代表所有并发调用执行完成。否则如果在规定时间内计数器没有归零，则代表并发调用超时，此时会抛出异常
 *
 *
 * 潜在问题
 * 并发调用的一个问题在于我们放大了访问下游接口的流量，极端情况下甚至放大了成百上千倍。如果下游服务并没有做限流等防御性措施，我们极有可能将下游服务打挂（这种原因导致的故障屡见不鲜）。
 * 因此需要对整个并发调用做流量控制。
 * 流量控制的方法有两种，一种是如果微服务采用mesh的模式，则可以在sidecar中配置RPC调用的QPS，
 * 从而做到全局的管控对下游服务的访问（这里选择单机限流还是集群限流取决于sidecar是否支持的模式以及服务的流量大小。通常来说平均流量较小则建议选择单机限流，
 * 因为集群限流的波动性往往比单机限流要高，流量过小会造成误判）。如果没有开启mesh，则需要在代码中自己实现限流器，这里推荐Guava的RateLimiter类，
 * 但是它只支持单机限流，如果要想实现集群限流，则方案的复杂度还会进一步提升
 */
public class BatchOperateCallable<T, R> implements Callable<R> {

    private final CountDownLatch countDownLatch;

    private final Function<T, R> function;

    private final T request;

    /**
     * 该线程处理是否成功
     */
    private boolean success;

    public BatchOperateCallable(CountDownLatch countDownLatch, Function<T, R> function, T request) {
        this.countDownLatch = countDownLatch;
        this.function = function;
        this.request = request;
    }

    @Override
    public R call() {
        try {
            success = false;
            R result = function.apply(request);
            success = true;
            return result;
        } finally {
            countDownLatch.countDown();
        }
    }

    public boolean isSuccess() {
        return success;
    }
}
