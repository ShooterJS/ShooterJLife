package com.shooterj.job.repeat.service;

import com.shooterj.cache.RedisService;
import com.shooterj.job.repeat.job.RepeatJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepeatService {

    @Autowired
    RedisService redisService;

    @RepeatJob(expire = 20,uniqueAppName = "EIP-TESTJOB")
    public void testAop(){

        String key = "testaa";
        try {
            boolean success = redisService.setnx(key, "", 30);
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
            redisService.delByKey(key);

        }
    }
}
