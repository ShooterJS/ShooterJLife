package com.winway.demo.design.wwabstract;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TestController {



    @RequestMapping(value = "message")
    public void test(){

    }

}
