package com.winway.demo.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题
 * 主题：观察者 = 1：N
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

    public void setState(int state){
        this.state = state;
        notifyAllObservers();
    }

    //业务方法
    public void notifyAllObservers(){
        observerList.stream().forEach(observer -> {
            observer.update();
        });
    }


}
