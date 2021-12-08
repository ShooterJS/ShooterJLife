package com.shooterj.utils;

import com.winway.meetingbase.common.util.AppUtil;
import com.shooterj.jwt.JwtTokenHandler;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Author: FangGan
 * @Email: libayiv@163.com
 * @Date: 2020/12/05 14:53
 **/
public class JwtTokenUtils {

    public static String decodeBase64Token(String token) {
        if (token == null) {
            token = StringUtils.EMPTY;
        }
        byte[] bytes = Base64.decodeBase64(token.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String generateToken(String username) {

        Objects.requireNonNull(username, "Username cannot be null");

        JwtTokenHandler jwtTokenHandler = AppUtil.getBean(JwtTokenHandler.class);

        Objects.requireNonNull(jwtTokenHandler, "JwtTokenHandler cannot be null");

        return jwtTokenHandler.generateToken(username);
    }
}
