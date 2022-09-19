package com.shooterj.common.limit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 限流算法：计数器 Semaphore实现
 * 计数器采用简单的计数操作，到一段时间节点后自动清零
 * <p>
 * 缺点：控制力度太过于简略，假如1s内限制3次，那么如果3次在前100ms内已经用完，后面的900ms将只能处于阻
 * 塞状态，白白浪费掉
 * 实现：首次可以放5个请求，接下来1秒内只允许3个请求，其余的都阻塞；
 */
public class Counter {

    public static void main(String[] args) throws InterruptedException {
        //计数器，这里用信号量实现
        Semaphore semaphore = new Semaphore(3);

        //定时器，去放资源
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                semaphore.release(3);
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);

        ////模拟无数个请求
        while (true) {
            //判断计数器有没有资源，没有则阻塞
            semaphore.acquire();
            //如果准许响应，打印一个ok
            System.out.println("ok");
        }
    }


}
