package com.winway.demo.spring.event;


import com.winway.demo.mapper.UserMapper;
import com.winway.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 场景：当成功添加一条用户信息后，将发送一条邮件到用户的邮箱账户。首先，需要定义两个实体,用户实体和邮件详情实体
 * 1.发布事件
 * 2.监听事件
 * 3.处理监听中的事件
 */
@RestController
public class SendEmailController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/sendEmailEvent")
    @Transactional
    public User sendEmailEvent(@RequestBody User user){
        UserContext.getInstance().put(user.getId(),user);
        userMapper.insert(user);
        System.out.println("开始发布事件");
        applicationEventPublisher.publishEvent(new SendEmailEvent(this,user.getId()));
        System.out.println("发布事件完毕");
        return user;
    }


}
