package com.winway.demo.design.wwabstract;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public  class PostTest {
    @PostConstruct
    public void test(){
        System.out.println("测试启动");
    }
}
