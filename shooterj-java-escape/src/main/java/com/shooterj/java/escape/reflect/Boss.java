package com.shooterj.java.escape.reflect;

/**
 * <h1>继承自 Worker 的 Boss 对象</h1>
 * */
public class Boss extends Worker {

    public String boss(String hello) {
        return Boss.class.getName() + ": " + hello;
    }

    public String numeric(int age) {
        return Boss.class.getName() + ": " + age;
    }
}
