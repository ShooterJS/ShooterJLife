package com.shooterj.java.escape.thread_safety.threadpool;

import lombok.SneakyThrows;

public class Reading implements Runnable {

    private String name;
    private Integer count;

    public Reading(Integer count, String name) {
        this.name = name;
        this.count = count;
    }

    @SneakyThrows
    @Override
    public void run() {

        while (count > 0) {

            System.out.println(Thread.currentThread().getName() + " Reading " + name);

            Thread.sleep(1000);

            --count;
        }

    }
}
