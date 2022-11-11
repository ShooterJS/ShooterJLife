package com.shooterj.design.singleton;

/**
 * 饿汉式
 */
public class HungerSingleton {
    public static HungerSingleton singleton = new HungerSingleton();
    private HungerSingleton(){}
    public static HungerSingleton getInstance(){
        return singleton;
    }
}
