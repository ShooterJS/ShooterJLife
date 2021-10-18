package com.shooterj.rpc;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/10/18
 *
 * batchOperate方法中传入了function对象，这是需要并发执行的代码逻辑。
 * requests则是所有的请求，并发调用会递归这些请求并提交到异步线程。config对象则可以对这次并发调用做一些配置，
 * 比如并发查询的超时时间，以及如果部分调用异常时整个批量查询是否继续执行
 */
@Data
public class BatchOperateConfig {

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 超时时间单位
     */
    private TimeUnit timeoutUnit;

    /**
     * 是否需要全部执行成功
     */
    private Boolean needAllSuccess;

}
