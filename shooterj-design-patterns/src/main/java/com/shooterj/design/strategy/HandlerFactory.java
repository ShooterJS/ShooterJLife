package com.shooterj.design.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HandlerFactory {

    private static Map<String, AbstractHandler> map = new ConcurrentHashMap();

    public static AbstractHandler getHandlerByName(String name) {
        return map.get(name);
    }

    public static void register(String handlerName, AbstractHandler handler) {
        map.put(handlerName, handler);
    }

    /*@PostConstruct
    public void init() {
        map.put(HandlerEnum.ONE.getHandlerName(), new OneHandler());
        map.put(HandlerEnum.TWO.getHandlerName(), new TwoHandler());
    }*/

}
