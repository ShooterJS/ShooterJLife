package com.shooterj.java.escape.thread_safety.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;

public class Main {



    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3, true);

        Producer producer = new Producer(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);

        new Thread(producer).start();

        new Thread(consumer).start();

    }
}
