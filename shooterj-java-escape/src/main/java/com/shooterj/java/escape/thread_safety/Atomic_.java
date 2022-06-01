package com.shooterj.java.escape.thread_safety;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程下变量更新安全
 */
public class Atomic_ {

    private static int count = 0;


    private static AtomicInteger atomicCount = new AtomicInteger(0);

    public static void accumulator(int acc) throws InterruptedException {

        CountDownLatch cd = new CountDownLatch(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < acc; i++) {
                    count++;
                }

                cd.countDown();
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < acc; i++) {
                    count++;
                }

                cd.countDown();
            }
        });

        t1.start();
        t2.start();


        cd.await();

        System.out.println("计数为:" + count);
    }

    /**
     * 线程安全
     */
    public static void automicAccumulator(int acc) throws InterruptedException {
        CountDownLatch cd = new CountDownLatch(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < acc; i++) {
                    atomicCount.incrementAndGet();
                }

                cd.countDown();
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < acc; i++) {
                    atomicCount.incrementAndGet();
                }

                cd.countDown();
            }
        });

        t1.start();
        t2.start();


        cd.await();

        System.out.println("计数为:" + atomicCount.get());
    }


    public static void main(String[] args) throws InterruptedException {
//        accumulator(20000);
        automicAccumulator(20000);
    }


}
