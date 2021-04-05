package com.winway.demo.spring.health;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
@Slf4j
public class UserServiceHealthIndicator implements HealthIndicator {
    @Resource
    private RestTemplate restTemplate;

    @Override
    public Health health() {
        long begin = System.currentTimeMillis();
        long userId = 1L;
        User user = null;
        try {
            //访问远程接口
            user = restTemplate.getForObject("http://localhost:8080/user/getUser?userId=" + userId, User.class);
            if (user != null && user.getUserId() == userId) {
                //结果正确，返回UP状态，补充提供耗时和用户信息
                return Health.up()
                        .withDetail("user", user)
                        .withDetail("took", System.currentTimeMillis() - begin)
                        .build();
            } else {
                //结果不正确，返回DOWN状态，补充提供耗时
                return Health.down().withDetail("took", System.currentTimeMillis() - begin).build();
            }
        } catch (Exception ex) {
            //出现异常，先记录异常，然后返回DOWN状态，补充提供异常信息和耗时
            log.warn("health check failed!", ex);
            return Health.down(ex).withDetail("took", System.currentTimeMillis() - begin).build();
        }
    }
}
