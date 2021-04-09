package com.winway.demo.design.observer;

/**
 * 观察者
 */
public abstract class WWObserver {

    /**
     * 观察的主题对象
     */
    WWSubject subject;

    /**
     * 主题变化，触发观察者更新抽象方法
     */
    public abstract void update();


}
