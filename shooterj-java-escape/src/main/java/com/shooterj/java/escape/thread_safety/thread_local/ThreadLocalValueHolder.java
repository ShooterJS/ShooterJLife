package com.shooterj.java.escape.thread_safety.thread_local;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在线程池中使用 ThreadLocal
 */public class ThreadLocalValueHolder {

    private static final ThreadLocal<Integer> holder = ThreadLocal.withInitial(
            () -> 0
    );

    public static int getValue() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }

    public static void increment() {
        holder.set(holder.get() + 1);
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i != 3; ++i) {

            executor.execute(
                    () -> {
                        try {
                            long threadId = Thread.currentThread().getId();
                            int before = getValue();
                            increment();
                            int after = getValue();

//                        System.out.println("before: " + before + ", after: " + after);
                            System.out.println("threadId: " + threadId +
                                    ", before: " + before + ", after: " + after);
                        } finally {
                            remove();
                        }
                    }
            );
        }

        executor.shutdown();
    }
}