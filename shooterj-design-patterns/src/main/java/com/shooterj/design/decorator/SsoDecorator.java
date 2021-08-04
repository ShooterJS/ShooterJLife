package com.shooterj.design.decorator;

import com.shooterj.design.decorator.intercepter.HandlerIntercepter;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/6/23
 */
public class SsoDecorator implements HandlerIntercepter {

    HandlerIntercepter intercepter;

    public SsoDecorator(HandlerIntercepter intercepter) {
        this.intercepter = intercepter;
    }

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        return intercepter.preHandle(request, response, handler);
    }
}
