package com.shooterj.design.observer;

/**
 * 思路
 * 1.注册所有观察者到主题 + 将主题信息共享到观察者
 * 2.调用观察者更新方法，观察者可拿到注入的主题来更新业务
 * 3.由于观察者有多种形态，所以用抽象类，抽象方法来抽象 更新方法和订阅公共的主题
 */
public class ObserverPatternTest {
    public static void main(String[] args) {
        WWSubject subject = new WWSubject();
        System.out.println("原始主题对象是"+subject.toString());

        //注册所有观察者到主题 + 将主题信息共享到观察者
        new HexaObserver(subject);
        new OctalObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);
    }
}
