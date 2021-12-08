package com.shooterj.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;


public class UniqueIdUtil {


    /** 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动） */
    private static final long epoch = 1603174599171L;
    /** 机器ID标识位数 */
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    private static final long SEQUENCE_BITS = 12L;/* 毫秒内自增位 */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /* 时间戳左移动位 */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /** 机器ID */
    private long workerId;

    /** 数据中心ID */
    private long dataCenterId;

    /** 并发控制 */
    private long sequence = 0L;

    /** 上次时间戳 */
    private long lastTimestamp = -1L;

    private static class InstanceHolder {
        public static final UniqueIdUtil INSTANCE = new UniqueIdUtil();
    }

    private static UniqueIdUtil instance() { return InstanceHolder.INSTANCE; }

    public static Long getSuid() {
        return instance().nextId();
    }

    public UniqueIdUtil() {
        this.dataCenterId = getDataCenterId(MAX_DATA_CENTER_ID);
        this.workerId = getMaxWorkerId(dataCenterId, MAX_WORKER_ID);
    }

    /**
     * @param workerId     工作机器ID
     * @param dataCenterId 序列号
     */
    public UniqueIdUtil(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            System.out.println(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            System.out.println(String.format("dataCenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * Get datacenter id
     */
    protected static long getDataCenterId(long maxDataCenterId) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            return ((0xFF & mac[mac.length - 1]) | (0xFF00 & mac[mac.length - 1] << 8)) >> 6 % (maxDataCenterId + 1);

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
            return System.currentTimeMillis() >> 32;
        }
    }

    /**
     * 获取 maxWorkerId
     */
    protected static long getMaxWorkerId(long dataCenterId, long maxWorkerId) {
        // JVM process pid
        String jvmPid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        // MAC + PID 的 hashcode 获取16个低位
        return ((dataCenterId + jvmPid).hashCode() & 0xffff) % (maxWorkerId + 1);
    }


    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {//闰秒
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }
        }

        if (lastTimestamp == timestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为 1 - 3 随机数
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << TIMESTAMP_LEFT_SHIFT)    // 时间戳部分
                | (dataCenterId << DATA_CENTER_ID_SHIFT)        // 数据中心部分
                | (workerId << WORKER_ID_SHIFT)                 // 机器标识部分
                | sequence;                                     // 序列号部分
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return SystemClock.now();
    }

    /**
     * 时钟
     */
    static class SystemClock {

        private final AtomicLong now;

        private SystemClock() {
            this.now = new AtomicLong(System.currentTimeMillis());
            /* 启动线程每毫秒获取一次机器时间 */
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    now.set(System.currentTimeMillis());
                }
            });
            thread.setDaemon(true);
            thread.start();
        }

        private static class InstanceHolder {
            public static final SystemClock INSTANCE = new SystemClock();
        }

        private static SystemClock instance() { return InstanceHolder.INSTANCE; }

        public static long now() { return instance().now.get(); }

    }

}
