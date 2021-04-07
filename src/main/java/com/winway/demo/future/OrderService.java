package com.winway.demo.future;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

/**
 * 1.订单服务请求(orderCode,SerialNo+future组装成对象)放入入队列BlockingQueue ,利用CompeleteFuture的get方法等待结果
 * 2.定时器每10秒拉取队列中的请求数据ScheduleThreadPool，批量发送给远程调用（不用频繁建立连接，关闭连接）
 * 3.远程接受到请求，处理后返回给调用方，循环比较serierNo,如果相等则将CompeleteFuture.compelte表示已接收到返回，
 */
@Component
@Slf4j
public class OrderService {

    @Autowired
    private RemoteServiceCall remoteServiceCall;
    public static Map<String, OrderInfo> orderMap = new ConcurrentHashMap();

    public LinkedBlockingQueue<FurureRequest> queue = new LinkedBlockingQueue();

    @PostConstruct
    public void initMap() {
        orderMap.put("123456", new OrderInfo("123456"));
    }

    public OrderInfo queryOrder(String orderCode) {
        OrderInfo orderInfo = orderMap.get(orderCode);
        synchronized (orderInfo) {
            orderInfo.setOrderTime(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(LocalDateTime.now()));
            orderInfo.setOrderMoney(new Random().nextInt(9999));
            System.out.println("查询的订单信息：" + JSON.toJSONString(orderInfo));
        }

        return orderInfo;
    }


    public Map<String, Object> queryOrderQueue(String orderCode) throws Exception {
        FurureRequest request = new FurureRequest();
        String serialNo = UUID.randomUUID().toString();
        request.orderCode = orderCode;
        request.serialNo = serialNo;
        CompletableFuture<Map<String, Object>> listenFuture = new CompletableFuture();
        request.listenFuture = listenFuture;

        queue.add(request);
        Map<String, Object> map = listenFuture.get();
        System.out.println("调用接口后返回:"+ JSON.toJSONString(map));
        return map;
    }


    /**
     * 定时器没10ms去队列中取数据
     */
    @PostConstruct
    public void init() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int size = queue.size();
                if (size == 0) {
                    return;
                }
                System.out.println("批量处理数据:" + size);
                List<FurureRequest> requestList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    FurureRequest request = queue.poll();
                    requestList.add(request);
                }
                //调用远程接口
                List<Map<String, Object>> responseList = remoteServiceCall.queryRemote(requestList);
                for (FurureRequest request : requestList) {
                    String serialNo = request.serialNo;
                    for (Map<String, Object> response : responseList) {
                        if (response.get("serialNo").equals(serialNo)) {
                            request.listenFuture.complete(response);
                            break;
                        }
                    }
                }
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }


    class FurureRequest {
        String orderCode;
        String serialNo;
        CompletableFuture listenFuture;
    }


    class OrderInfo {
        String orderCode;
        String orderTime;
        Integer orderMoney;

        public OrderInfo(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public Integer getOrderMoney() {
            return orderMoney;
        }

        public void setOrderMoney(Integer orderMoney) {
            this.orderMoney = orderMoney;
        }


    }
}
