package com.shooterj.design.strategy;

import com.shooterj.design.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    FormService formService;

    @org.junit.Test
    public void test1(){
        formService.submitForm(FormSubmitRequest.builder().submitType("preview").userId(88L).build());
    }
}
