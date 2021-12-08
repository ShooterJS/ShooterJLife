package com.shooterj.service;

import com.winway.meetingbase.common.util.JsonUtils;
import com.winway.meetingbase.redis.JedisPoolUtil;
import com.shooterj.uc.model.UserFacade;
import com.shooterj.uc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public static final String APP_NAME = "mci-server";

    @Resource
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDetails userDetails;
            // 缓存用户信息，默认60S
            String key = APP_NAME + username;
            try(Jedis jedis = JedisPoolUtil.getJedis()) {
                String result = jedis.get(key);
                if (result == null || result.isEmpty()) {
                    userDetails = userService.loadUserByUsername(username);
                    jedis.setex(key, 60, JsonUtils.toJson(userDetails));
                } else {
                    userDetails = JsonUtils.toBean(result, UserFacade.class);
                }
            }
            return userDetails;
        } catch (Exception e) {
            logger.error("UserService Exception: ", e);
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }
}