package com.shooterj.design.strategy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class StrategyTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testStrateagy() {
        AbstractHandler one = HandlerFactory.getHandlerByName("one");
//        AbstractHandler one = applicationContext.getBean(OneHandler.class);
        one.drive();
        one.eat();


    }






}
