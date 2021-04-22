package com.shooterj.design.strategy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class TwoHandler implements AbstractHandler, InitializingBean {
    @Override
    public void eat() {
        System.out.println("TwoHandler eat");
    }

    @Override
    public void drive() {
        System.out.println("TwoHandler drive");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerFactory.register(HandlerEnum.TWO.getHandlerName(), this);
    }
}
