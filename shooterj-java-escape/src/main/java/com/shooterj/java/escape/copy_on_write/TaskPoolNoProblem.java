package com.shooterj.java.escape.copy_on_write;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * <h1>并发读写会抛出异常</h1>
 */
public class TaskPoolNoProblem {

    private static List<String> tasks = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {


        for (int i = 0; i < 10; i++) {
            tasks.add("task:"+i);
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    tasks.add("task-x");
                }
            }
        });

        th.setDaemon(true);
        th.start();


        Thread.sleep(1000L);

        for (String task : tasks) {
            System.out.println(task);
        }


    }
}
