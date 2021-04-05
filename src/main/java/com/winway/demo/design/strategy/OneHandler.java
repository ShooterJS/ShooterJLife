package com.winway.demo.design.strategy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class OneHandler implements AbstractHandler, InitializingBean {
    @Override
    public void eat() {
        System.out.println("OneHandler eat");
    }

    @Override
    public void drive() {
        System.out.println("OneHandler drive");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerFactory.register(HandlerEnum.ONE.getHandlerName(), this);
    }
}
