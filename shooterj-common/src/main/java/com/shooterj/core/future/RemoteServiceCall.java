package com.shooterj.core.future;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RemoteServiceCall {

    public List<Map<String,Object>> queryRemote(List<OrderService.FurureRequest> requests){

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String,Object>> list = new ArrayList<>();
        for (OrderService.FurureRequest request : requests) {
            Map map = new HashMap();
            map.put("serialNo",request.serialNo);
            map.put("orderNo",request.orderCode);
            map.put("orderMoney",new Random().nextInt(9999));
            list.add(map);
        }
        return list;
    }

}
