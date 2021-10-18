package com.shooterj.rpc;

import lombok.Data;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 线程池准备就绪，接着我们就需要声明一个接口用于提交并发调用服务
 */
public interface BatchOperateService {

    /**
     * 并发批量操作
     * @param function 执行的逻辑
     * @param requests 请求
     * @param config 配置
     * @return 全部响应
     */
    <T, R> List<R> batchOperate(Function<T, R> function, List<T> requests, BatchOperateConfig config);
}


