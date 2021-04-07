package com.winway.demo.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题
 * 只要主题一变所有的观察者的数据都得跟着变化
 * 1.将所有的观察者注册进来，提供注册方法
 * 2.主题的业务方法
 * 3.遍历所有的观察者，调用观察者的update方法
 */
public class WWSubject {
    private int state;

    public int getState() {
        return state;
    }

    List<WWObserver> observerList = new ArrayList<WWObserver>();

    //提供注册方法
    public void register(WWObserver observer){
        observerList.add(observer);
    }

    //业务方法
    public void setState(int state){
        observerList.stream().forEach(observer -> {
            observer.update();
        });
    }


}
