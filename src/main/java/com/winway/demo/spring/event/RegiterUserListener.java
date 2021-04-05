package com.winway.demo.spring.event;

import com.winway.demo.model.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 可以使用@TransactionalEventListener注解对监听器进行标注，一旦使用此注解，被标注的方法将被纳入到事务管理的范围。
 * 此注解是@EventListener的扩展，它允许你将事件的监听绑定到某个事务阶段。可以通过phase属性对事务阶段进行设置。下面是phase可设置的所有事务阶段
 */
@Component
//异步执行监听器，提高性能
@Async
public class RegiterUserListener {

    //监听发布的事件,来处理其他的任务，做到解耦
    @EventListener
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT")
    public void sendEmail(SendEmailEvent event) throws Exception {
        User user = UserContext.getInstance().get(event.getId());
//        Thread.sleep(5000);
        System.out.println("监听事件，发送邮件给"+user.getName());
        throw new Exception("抛出自定义异常");
    }


}
