package com.shooterj.common.limit;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Window {
    //整个窗口的流量上限，超出会被限流
    final int totalMax = 5;
    //每片的流量上限，超出同样会被拒绝，可以设置不同的值
    final int sliceMax = 5;
    //分多少片
    final int slice = 3;
    //窗口，分3段，每段1s，也就是总长度3s
    final LinkedList<Long> linkedList = new LinkedList<>();
    //计数器，每片一个key，可以使用HashMap，这里为了控制台保持有序性和可读性，采用TreeMap
    Map<Long, AtomicInteger> map = new TreeMap();
    //心跳，每1s跳动1次，滑动窗口向前滑动一步，实际业务中可能需要手动控制滑动窗口的时机。
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    //获取key值，这里即是时间戳（秒）
    private Long getKey() {
        return System.currentTimeMillis() / 1000;
    }

    public Window() {
        //初始化窗口，当前时间指向的是最末端，前两片其实是过去的2s
        Long key = getKey();
        for (int i = 0; i < slice; i++) {
            linkedList.addFirst(key‐i);
            map.put(key‐i, new AtomicInteger(0));
        }
        //启动心跳任务，窗口根据时间，自动向前滑动，每秒1步
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Long key = getKey();
                //队尾添加最新的片
                linkedList.addLast(key);
                map.put(key, new AtomicInteger());
                //将最老的片移除
                map.remove(linkedList.getFirst());
                linkedList.removeFirst();
                System.out.println("step:" + key + ":" + map);
                ;
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    //检查当前时间所在的片是否达到上限
    public boolean checkCurrentSlice() {
        long key = getKey();
        AtomicInteger integer = map.get(key);
        if (integer != null) {
            return integer.get() < sliceMax;
        }
        //默认允许访问
        return true;
    }

    //检查整个窗口所有片的计数之和是否达到上限
    public boolean checkAllCount() {
        return map.values().stream().mapToInt(value ‐ > value.get()).sum() < totalMax;
    }

    //请求来临....
    public void req() {
        Long key = getKey();
        //如果时间窗口未到达当前时间片，稍微等待一下
        //其实是一个保护措施，放置心跳对滑动窗口的推动滞后于当前请求
        while (linkedList.getLast() < key) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //开始检查，如果未达到上限，返回ok，计数器增加1
        //如果任意一项达到上限，拒绝请求，达到限流的目的
        //这里是直接拒绝。现实中可能会设置缓冲池，将请求放入缓冲队列暂存
        if (checkCurrentSlice() && checkAllCount()) {
            map.get(key).incrementAndGet();
            System.out.println(key + "=ok:" + map);
        } else {
            System.out.println(key + "=reject:" + map);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Window window = new Window();
        //模拟10个离散的请求，相对之间有200ms间隔。会造成总数达到上限而被限流
        for (int i = 0; i < 10; i++) {
            Thread.sleep(200);
            window.req();
        }
        //等待一下窗口滑动，让各个片的计数器都置零
        Thread.sleep(3000);
        //模拟突发请求，单个片的计数器达到上限而被限流
        System.out.println("‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐");
        for (int i = 0; i < 10; i++) {
            window.req();
        }
    }

}
