package com.shooterj.job.repeat.test;

import com.shooterj.job.repeat.aop.RepeatJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RepeatJobTest {


    @Scheduled(cron = "0/5 * * * * ? ")
    @RepeatJob(expire = 20,uniqueAppName = "EIP-TESTJOB")
    public void testRepeatJob() {
        System.out.println("testRepeatJob process end.....");
    }
}
