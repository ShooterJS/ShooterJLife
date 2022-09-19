package com.shooterj.core.multthread.countdown;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ParallelFetcher {
    final long timeout;
    final CountDownLatch latch;
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(100));

    public ParallelFetcher(int jobSize, long timeoutMill) {
        latch = new CountDownLatch(jobSize);
        timeout = timeoutMill;
    }

    public void submitJob(Runnable runnable) {
        executor.execute(() -> {
            runnable.run();
            latch.countDown();
        });
    }

    public void await() {
        try {
            this.latch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    public void dispose() {
        this.executor.shutdown();
    }

    public static void main(String[] args) {
        final String userid = "123";
        final SlowInterfaceMock mock = new SlowInterfaceMock();
        ParallelFetcher fetcher = new ParallelFetcher(5, 50);
        Map<String, String> result = new HashMap<>();
        fetcher.submitJob(() -> result.put("method0", mock.method0(userid)));
        fetcher.submitJob(() -> result.put("method1", mock.method1(userid)));
        fetcher.submitJob(() -> result.put("method2", mock.method2(userid)));
        fetcher.submitJob(() -> result.put("method3", mock.method3(userid)));
        fetcher.submitJob(() -> result.put("method4", mock.method4(userid)));
        fetcher.await();
        System.out.println(fetcher.latch);
        System.out.println(result.size());
        System.out.println(result);
        fetcher.dispose();
    }
}