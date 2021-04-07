package com.winway.demo.design.strategy;

import com.winway.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class StrategyTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testStrateagy() {
//        AbstractHandler one = HandlerFactory.getHandlerByName("one");
        AbstractHandler one = applicationContext.getBean(OneHandler.class);
        one.drive();
        one.eat();


       /* AbstractHandler two = HandlerFactory.getHandlerByName("two");
        two.drive();
        two.eat();*/
    }






}
