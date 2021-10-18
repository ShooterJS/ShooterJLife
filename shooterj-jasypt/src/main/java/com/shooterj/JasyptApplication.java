package com.shooterj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/8/12
 */
//@SpringBootApplication( exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class JasyptApplication {

    public static void main(String[] args) {
        SpringApplication.run(JasyptApplication.class, args);
    }
}
