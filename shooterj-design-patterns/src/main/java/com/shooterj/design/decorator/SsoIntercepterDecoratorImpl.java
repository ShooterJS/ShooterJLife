package com.shooterj.design.decorator;

import com.shooterj.design.decorator.intercepter.HandlerIntercepter;
import com.shooterj.design.decorator.intercepter.SsoIntercepter;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/6/23
 */
public class SsoIntercepterDecoratorImpl extends SsoDecorator {


    public SsoIntercepterDecoratorImpl(HandlerIntercepter intercepter) {
        super(intercepter);
    }

    @Override
    public boolean preHandle(String request, String response, Object handler) {
        boolean nameReal = super.preHandle(request, response, handler);
        if (!nameReal) {
            return false;
        }
        System.out.println("账号验证完毕");


        //TODO 验证路径省略 dosomething
        System.out.println("验证路径完毕");

        return true;
    }

    public static void main(String[] args) {
        SsoIntercepterDecoratorImpl ssoIntercepterDecorator = new SsoIntercepterDecoratorImpl(new SsoIntercepter());
        ssoIntercepterDecorator.preHandle("ShooterJ","",null);
    }
}
