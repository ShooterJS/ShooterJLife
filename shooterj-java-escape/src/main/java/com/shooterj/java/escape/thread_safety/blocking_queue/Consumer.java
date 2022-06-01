package com.shooterj.java.escape.thread_safety.blocking_queue;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {


    private BlockingQueue<Integer> blockingQueue;

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

//    @Override
//    public void run() {
//        while (true) {
//            Integer value = blockingQueue.poll();
//            System.out.println("Consume: " + value);
//            if (value >= 99) {
//                break;
//            }
//        }
//
//        System.out.println("Consumer Done!");
//    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Integer value = blockingQueue.take();
            System.out.println("Consume: " + value);
            if (value >= 99) {
                break;
            }
        }

        System.out.println("Consumer Done!");
    }

}
