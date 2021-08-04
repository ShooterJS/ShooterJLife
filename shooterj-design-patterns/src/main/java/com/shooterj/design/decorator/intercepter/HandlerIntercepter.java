package com.shooterj.design.decorator.intercepter;

/**
 * 首先自定义一个登录拦截器接口，模拟spring
 * 实现拦截器验证登录名的基本功能（判断是否为登录名lisi）
 *  问题1:假设现在要增加一个判断是否有访问方法权限，但不能修改之前拦截器逻辑
 *  装饰器模式
 *  开发一个登录装饰器实现类，实现之前登录接口的方法，将处理类注入到现有的装饰器中，先处理验证登录名，再去做验证方法访问权限
 *
 */
public interface HandlerIntercepter {

    boolean preHandle(String request, String response, Object handler);
}
