package com.winway.demo.design.observer;

/**
 * 观察者
 * 首先观察主题，得有主题注入
 */
public abstract class WWObserver {

     WWSubject wwSubject;
     public abstract void update();


}
