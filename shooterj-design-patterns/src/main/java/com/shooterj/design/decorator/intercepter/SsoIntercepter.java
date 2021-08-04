package com.shooterj.design.decorator.intercepter;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/6/23
 */
public class SsoIntercepter implements HandlerIntercepter {
    @Override
    public boolean preHandle(String request, String response, Object handler) {
        return "ShooterJ".equals(request);
    }
}
