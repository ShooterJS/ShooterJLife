package com.shooterj.java.escape.thread_safety.blocking_queue;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue blockingQueue;

    private static int element = 0;


    public Producer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /*@Override
    public void run() {
        while (element < 100) {
            System.out.println("Produce: " + element);
            blockingQueue.offer(element++);
        }

        System.out.println("Produce Done!");
    }*/

    @SneakyThrows
    @Override
    public void run() {
        while (element < 100) {
            System.out.println("Produce: " + element);
            blockingQueue.put(element++);
        }

        System.out.println("Produce Done!");
    }
}
