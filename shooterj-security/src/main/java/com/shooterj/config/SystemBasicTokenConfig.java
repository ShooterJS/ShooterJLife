package com.shooterj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: FangGan
 * @Email: libayiv@163.com
 * @Date: 2020/12/05 15:01
 **/
@Configuration
public class SystemBasicTokenConfig {

    public static String BASIC_TOKEN;

    @Value("${system.basic.token}")
    public void setBasicToken(String basicToken) {
        BASIC_TOKEN = basicToken;
    }
}
