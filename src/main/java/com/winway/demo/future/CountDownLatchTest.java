package com.winway.demo.future;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟1000个线程同时请求接口
 * 思路、：使用CountDownLatch  所有进来的请求用await先阻塞。当一千个请求全部集结完成。将调用countDwon方法。同时请求接口
 */
@Component
public class CountDownLatchTest {

    @Autowired
    OrderService orderService;

    private CountDownLatch cdl = new CountDownLatch(1);

    public static final Integer MAX_THREAD = 1000;

    public void test() throws Exception{
        //模拟1000个请求
        for (int i = 0; i < MAX_THREAD; i++) {
                new Thread(() -> {
                    try {
                        //来一个线程阻塞一个，不让其调接口
                        cdl.await();
                        //调用查询接口
//                        orderService.queryOrder("123456");
                        Map<String, Object> map = orderService.queryOrderQueue("123456");
                        System.out.println("调用接口后返回:"+ JSON.toJSONString(map));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }).start();
        }

        //将cdl从1减到0，这个时候1000线程放开，同时请求queryOrder接口
        cdl.countDown();
        System.out.println("大爷先休息几秒");
        Thread.sleep(2000);
        System.out.println("大爷休息完了");
    }

}
