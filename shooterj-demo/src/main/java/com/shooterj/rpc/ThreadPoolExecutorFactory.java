package com.shooterj.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/10/18
 * 首先我们需要创建一个线程池用于并发执行。因为程序中通常还有别的使用线程池的场景，而我们希望RPC调用能够使用一个单独的线程池，因此这里用工厂方法进行了封装。
 *
 * 我们声明了两个Spring的线程池AsyncTaskExecutor，分别是默认的线程池和RPC调用的线程池，并将它们装载到map中。
 * 调用方可以使用fetchAsyncTaskExecutor方法并传入线程池的名称来指定线程池执行。这里还有一个细节，
 * Rpc线程池的线程数要显著大于另一个线程池，是因为Rpc调用不是CPU密集型逻辑，往往伴随着大量的等待。因此增加线程数量可以有效提高并发效率
 */
@Configuration
public class ThreadPoolExecutorFactory {

    @Resource
    private Map<String, AsyncTaskExecutor> executorMap;

    /**
     * 默认的线程池
     */
    @Bean(name = ThreadPoolName.DEFAULT_EXECUTOR)
    public AsyncTaskExecutor baseExecutorService() {
        //后续支持各个服务定制化这部分参数
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数信息
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix(ThreadPoolName.DEFAULT_EXECUTOR + "--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.setDaemon(Boolean.TRUE);
        //修改拒绝策略为使用当前线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        taskExecutor.initialize();

        return taskExecutor;
    }

    /**
     * 并发调用单独的线程池
     */
    @Bean(name = ThreadPoolName.RPC_EXECUTOR)
    public AsyncTaskExecutor rpcExecutorService() {
        //后续支持各个服务定制化这部分参数
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数信息
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix(ThreadPoolName.RPC_EXECUTOR + "--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.setDaemon(Boolean.TRUE);
        //修改拒绝策略为使用当前线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        taskExecutor.initialize();

        return taskExecutor;
    }
    /**
     * 根据线程池名称获取线程池
     * 若找不到对应线程池，则抛出异常
     * @param name 线程池名称
     * @return 线程池
     * @throws RuntimeException 若找不到该名称的线程池
     */
    public AsyncTaskExecutor fetchAsyncTaskExecutor(String name) {
        AsyncTaskExecutor executor = executorMap.get(name);
        if (executor == null) {
            throw new RuntimeException("no executor name " + name);
        }
        return executor;
    }
}


