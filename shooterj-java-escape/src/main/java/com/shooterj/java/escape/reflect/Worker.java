package com.shooterj.java.escape.reflect;

/**
 * <h1>继承自 People 的 Worker 对象</h1>
 * */
public class Worker extends People {

    public String worker(String hello) {
        return Worker.class.getName() + ": " + hello;
    }
}
