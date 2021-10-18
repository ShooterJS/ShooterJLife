package com.shooterj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 作者:ShooterJ
 */
@EnableAsync
@SpringBootApplication
@Configuration
@MapperScan(basePackages={"com.shooterj.**.mapper"})
@ComponentScan({"com.shooterj.**"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
