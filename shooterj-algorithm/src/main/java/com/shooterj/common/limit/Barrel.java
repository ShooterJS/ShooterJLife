package com.shooterj.common.limit;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 漏桶算法
 * 漏桶算法将请求缓存在桶中，服务流程匀速处理。超出桶容量的部分丢弃。漏桶算法主要用于保护内部的处理业
 * 务，保障其稳定有节奏的处理请求，但是无法根据流量的波动弹性调整响应能力。现实中，类似容纳人数有限的服
 * 务大厅开启了固定的服务窗口。
 * 实现：最多一次只能处理3个请求，多的请求都限制请求
 */
public class Barrel {

    public static void main(String[] args) throws Exception {
        //初始化队列
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(3);

        //按固定频率去处理请求
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Integer v = queue.poll();
                System.out.println("处理：" + v);

            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);

        //放
        int i = 0;
        while (true) {
            //如果是put，会一直等待桶中有空闲位置，不会丢弃
//            que.put(i);
//            等待1s如果进不了桶，就溢出丢弃
            queue.offer(i, 1000, TimeUnit.MILLISECONDS);
            i++;
        }


    }
}
