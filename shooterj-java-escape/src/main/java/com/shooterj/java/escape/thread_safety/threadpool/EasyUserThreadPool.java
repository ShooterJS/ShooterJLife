package com.shooterj.java.escape.thread_safety.threadpool;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * 简单使用线程池
 */
public class EasyUserThreadPool {


    private static void useFixedThreadPool(int threadCount) {

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);


        Runnable runnable01 = new Reading(3, "Java 编程思想");
        Runnable runnable02 = new Reading(2, "Spring 实战");
        Runnable runnable03 = new Reading(3, "SpringBoot 实战");
        Runnable runnable04 = new Reading(1, "MySQL 权威指南");
        Runnable runnable05 = new Reading(2, "SpringCloud 实战");

        executor.execute(runnable01);
        executor.execute(runnable02);
        executor.execute(runnable03);
        executor.execute(runnable04);
        executor.execute(runnable05);

        executor.shutdown();

    }


    /**
     * <h2>自定义线程池</h2>
     */
    private static void customThreadPool() {

//        ThreadPoolExecutor customer1 = new ThreadPoolExecutor(1, 1, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(2));

        ThreadPoolExecutor customer1 = new ThreadPoolExecutor(1, 1, 30, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(2), new CustomRejectHandler());

        for (int i = 0; i != 5; ++i) {
            customer1.execute(new Reading(3, "JAVA编程思想"));
        }
        customer1.shutdown();


    }

    private static class CustomRejectHandler implements RejectedExecutionHandler {

        @SneakyThrows
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            executor.getQueue().put(r);
        }
    }


    public static void main(String[] args) {
//      useFixedThreadPool(3);

        customThreadPool();

    }
}
