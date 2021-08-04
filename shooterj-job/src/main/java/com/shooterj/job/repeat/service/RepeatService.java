package com.shooterj.job.repeat.service;

import com.shooterj.cache.impl.RedisCache;
import com.shooterj.job.repeat.aop.RepeatJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepeatService {

    @Autowired
    RedisCache redisCache;

    @RepeatJob(expire = 20,uniqueAppName = "EIP-TESTJOB")
    public void testAop(){

        String key = "testaa";
        try {
            boolean success = redisCache.setIfAbsent(key,  "30");
            if (success) {
                System.out.println("成功加锁，执行请求。。。。。。。。。。。。");
            } else {
                //没有抢到锁则打回，不让重复执行
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //执行完后删除key，等待下一次定时器任务的执行
            redisCache.del(key);

        }
    }
}
