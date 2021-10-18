package com.shooterj;

import java.util.concurrent.CountDownLatch;

/**
 * 已知一个业务查询操作涉及 3 个 RPC 服务调用 : query1, query2, query3, 其中
 * query1 耗时约 1 秒， query2 耗时约 0.5 秒，query3 耗时约 0.6 秒，
 * 且 query3查询条件依赖 query2 的查询结果，
 * 请编写代码，使该业务查询总体耗时最小。
 *
 * 本题比较简单，主要考察知识点异步和多线程控制。
 * 如果采用串行执行，query1+query2+query3…总耗时为 2.1 秒。 …
 * 采用多线程异步并行执行，使用线程 A 请求 query1，同时使用线程 B 请求 query2
 * 后再请求 query3（query3 依赖…query2 结果只能串行执行），
 * 这样总耗时是 min(1,…0.5+0.6)…=…1.1 秒。 ……需要熟悉 join,…CountDownLatch 等线程
 * 协调控制方法，如果考生使用线程池则更佳。
 *
 */
public class RpcRequestOptimize {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        Long s = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName()+"执行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"query1").start();

        Thread query2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+"执行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "query2");
        query2.start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    query2.join();
                    Thread.sleep(600);
                    System.out.println(Thread.currentThread().getName()+"执行结束");
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"query3").start();


        try {
            countDownLatch.await();
            System.out.println("请求结束 耗时 "+(System.currentTimeMillis()-s)+" ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
