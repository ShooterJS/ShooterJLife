package com.shooterj.rpc;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/10/18
 * submit方法封装了获取线程池和提交异步任务的逻辑。这里采用Callable+Future的组合来获取异步线程的执行结果
 * 线程池准备就绪，接着我们就需要声明一个接口用于提交并发调用服务
 */
@Component
public class TracedExecutorService {

    @Resource
    private ThreadPoolExecutorFactory threadPoolExecutorFactory;


    /**
     * 指定线程池提交异步任务，并获得任务上下文
     * @param executorName 线程池名称
     * @param tracedCallable 异步任务
     * @param <T> 返回类型
     * @return 线程上下文
     */
    public <T> Future<T> submit(String executorName, Callable<T> tracedCallable) {
        return threadPoolExecutorFactory.fetchAsyncTaskExecutor(executorName).submit(tracedCallable);
    }
}
