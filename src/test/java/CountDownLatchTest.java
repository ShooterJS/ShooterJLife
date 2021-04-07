import com.alibaba.fastjson.JSON;
import com.winway.demo.DemoApplication;
import com.winway.demo.future.OrderService;
import com.winway.demo.repeat.service.RepeatService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟1000个线程同时请求接口
 * 思路、：使用CountDownLatch  所有进来的请求用await先阻塞。当一千个请求全部集结完成。将调用countDwon方法。同时请求接口
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Component
public class CountDownLatchTest {

    @Autowired
    OrderService orderService;
    @Autowired
    RepeatService repeatService;

    private CountDownLatch cdl = new CountDownLatch(1);

    public static final Integer MAX_THREAD = 10;

    @Test
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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }).start();
        }

        //将cdl从1减到0，这个时候1000线程放开，同时请求queryOrder接口
        cdl.countDown();
        System.out.println("主线程先休息一下");
        Thread.sleep(2000);
        System.out.println("主线程休息完了");
    }


    @Test
    @Ignore
    public void testRepeatAop() throws Exception{
        for (int i = 0; i < MAX_THREAD; i++) {
            new Thread(() -> {
                try {
                    //来一个线程阻塞一个，不让其调接口
                    cdl.await();
                    //调用查询接口
                    repeatService.testAop();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
        //执行调用
        cdl.countDown();

    }

}
