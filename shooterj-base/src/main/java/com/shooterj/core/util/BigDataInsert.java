package com.shooterj.core.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 批量插入百万数量级的数据到mysql的解决方案
 * @param <T>
 */
@Slf4j
@Data
public abstract class BigDataInsert<T> {
    public String driverClassName = "";
    public String url = "";
    public String user = "";
    public String password = "";
    public String sql = "";

    int groupCount = 100000;
    int threadPoolCount = 5;

    // 创建一个固定大小的线程池
    private ExecutorService service = null;

    public BigDataInsert() {
        init();
    }

    public void insertBigData(List<T> list) {
        // 检查初始化参数
        checkInit();
        // 创建线程池对象
        service = Executors.newFixedThreadPool(threadPoolCount);
        // 将需保存集合分组
        List<List<T>> listList = new ArrayList<>();
        if (list.size() > groupCount) {
            listList = fixedGrouping(list, groupCount);
        } else {
            listList.add(list);
        }
        // 计数器
        final CountDownLatch latch = new CountDownLatch(listList.size());

        //开始总计时
        long bTime1 = System.currentTimeMillis();
        //循环10次，每次十万数据，一共100万
        for (int i = 0; i < listList.size(); i++) {
            int finalI = i;
            List<List<T>> finalListList = listList;
            // 多线程保存
            service.execute(() -> {
                Connection conn = null;
                PreparedStatement pstm = null;
                try {
                    //加载jdbc驱动
                    Class.forName(driverClassName);
                    //连接mysql
                    conn = DriverManager.getConnection(url, user, password);
                    //将自动提交关闭
                    conn.setAutoCommit(false);
                    //预编译sql
                    pstm = conn.prepareStatement(sql);
                    //开启分段计时，计1W数据耗时
                    long bTime = System.currentTimeMillis();
                    //开始循环
                    for (T object : finalListList.get(finalI)) {
                        //赋值
                        pstmToSetValue(pstm, object);
                        //添加到同一个批处理中
                        pstm.addBatch();
                    }
                    //执行批处理
                    pstm.executeBatch();
                    //提交事务
                    conn.commit();
                    //关闭分段计时
                    long eTime = System.currentTimeMillis();
                    //输出
                    System.out.println("成功插入" + finalListList.get(finalI).size() + "条数据耗时：" + (eTime - bTime));
                } catch (Exception e) {
                    log.error("批量保存失败！",e);
                } finally {
                    latch.countDown();
                    try {
                        pstm.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            latch.await();
        } catch (Exception e) {
            log.error("多线程分析数据中途异常!,{}", e);
        }
        //关闭总计时
        long eTime1 = System.currentTimeMillis();
        //输出
        System.out.println("插入" + list.size() + "数据共耗时：" + (eTime1 - bTime1));

    }

    private void checkInit() {
        if ("".equals(driverClassName)) {
            log.warn("driverClassName未初始化！");
        }
        if ("".equals(url)) {
            log.warn("url未初始化！");
        }
        if ("".equals(user)) {
            log.warn("user未初始化！");
        }
        if ("".equals(password)) {
            log.warn("password未初始化！");
        }
        if ("".equals(sql)) {
            log.warn("sql未设置！");
        }
    }

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

    public abstract PreparedStatement pstmToSetValue(PreparedStatement pstm, T object);

    public abstract void init();

}
