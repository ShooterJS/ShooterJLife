package com.winway.demo.spring.health.contributor;


import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ThreadPoolInfoContributor implements InfoContributor {

    private static Map threadPoolInfo(ThreadPoolExecutor threadPool) {
        Map<String, Object> info = new HashMap<>();
        //当前池大小
        info.put("poolSize", threadPool.getPoolSize());
        //设置的核心池大小
        info.put("corePoolSize", threadPool.getCorePoolSize());
        //最大达到过的池大小
        info.put("largestPoolSize", threadPool.getLargestPoolSize());
        //设置的最大池大小
        info.put("maximumPoolSize", threadPool.getMaximumPoolSize());
        //总完成任务数
        info.put("completedTaskCount", threadPool.getCompletedTaskCount());
        return info;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("demoThreadPool", threadPoolInfo(ThreadPoolProvider.getDemoThreadPool()));
        builder.withDetail("ioThreadPool", threadPoolInfo(ThreadPoolProvider.getIOThreadPool()));
    }
}