package com.shooterj.core.threadpool;

import com.shooterj.core.util.WWObjectUtil;

import java.util.concurrent.*;

public class ThreadPoolBuilder implements BaseBuilder {

    /**
     * 默认的等待队列容量
     */
    public static final int DEFAULT_QUEUE_CAPACITY = 1024;

    /**
     * 初始池大小
     */
    private int corePoolSize;
    /**
     * 最大池大小（允许同时执行的最大线程数）
     */
    private int maxPoolSize = Integer.MAX_VALUE;
    /**
     * 线程存活时间，即当池中线程多于初始大小时，多出的线程保留的时长
     */
    private long keepAliveTime = TimeUnit.SECONDS.toNanos(60);
    /**
     * 队列，用于存放未执行的线程
     */
    private BlockingQueue<Runnable> workQueue;
    /**
     * 线程工厂，用于自定义线程创建
     */
    private ThreadFactory threadFactory;
    /**
     * 当线程阻塞（block）时的异常处理器，所谓线程阻塞即线程池和等待队列已满，无法处理线程时采取的策略
     */
    private RejectedExecutionHandler handler;
    /**
     * 线程执行超时后是否回收线程
     */
    private Boolean allowCoreThreadTimeOut;

    /**
     * 设置初始池大小，默认0
     *
     * @param corePoolSize 初始池大小
     * @return this
     */
    public ThreadPoolBuilder setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    /**
     * 设置最大池大小（允许同时执行的最大线程数）
     *
     * @param maxPoolSize 最大池大小（允许同时执行的最大线程数）
     * @return this
     */
    public ThreadPoolBuilder setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * 设置线程存活时间，即当池中线程多于初始大小时，多出的线程保留的时长
     *
     * @param keepAliveTime 线程存活时间
     * @param unit          单位
     * @return this
     */
    public ThreadPoolBuilder setKeepAliveTime(long keepAliveTime, TimeUnit unit) {
        return setKeepAliveTime(unit.toNanos(keepAliveTime));
    }

    /**
     * 设置线程存活时间，即当池中线程多于初始大小时，多出的线程保留的时长，单位纳秒
     *
     * @param keepAliveTime 线程存活时间，单位纳秒
     * @return this
     */
    public ThreadPoolBuilder setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public ThreadPoolBuilder setWorkQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    /**
     * 使用{@link ArrayBlockingQueue} 做为等待队列<br>
     * 有界队列，相对无界队列有利于控制队列大小，队列满时，运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
     *
     * @param capacity 队列容量
     * @return this
     * @since 5.1.4
     */
    public ThreadPoolBuilder useArrayBlockingQueue(int capacity) {
        return setWorkQueue(new ArrayBlockingQueue<>(capacity));
    }

    public static ThreadPoolBuilder create() {
        return new ThreadPoolBuilder();
    }

    @Override
    public ThreadPoolExecutor build() {
        return build(this);
    }

    public ThreadPoolExecutor build(ThreadPoolBuilder builder) {
        final int corePoolSize = builder.corePoolSize;
        final int maxPoolSize = builder.maxPoolSize;
        final long keepAliveTime = builder.keepAliveTime;
        final BlockingQueue<Runnable> workQueue;


        if (builder.workQueue == null) {
            // corePoolSize为0则要使用SynchronousQueue避免无限阻塞
            workQueue = (corePoolSize <= 0) ? new SynchronousQueue<>() : new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY);
        } else {
            workQueue = builder.workQueue;
        }

        final ThreadFactory threadFactory = WWObjectUtil.defaultIfNull(builder.threadFactory, Executors.defaultThreadFactory());
        RejectedExecutionHandler handler = WWObjectUtil.defaultIfNull(builder.handler, new ThreadPoolExecutor.AbortPolicy());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime, TimeUnit.NANOSECONDS,
                workQueue,
                threadFactory,
                handler);

        return executor;
    }


    public static void main(String[] args) {
        ThreadUtil.newExecutor(1, 4, 10);
    }

}
