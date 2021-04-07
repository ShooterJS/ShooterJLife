package com.winway.demo.design.observer;

import javax.security.auth.Subject;

public class ObserverPatternTest {
    public static void main(String[] args) {
        WWSubject subject = new WWSubject();

        new HexaObserver(subject);
//        new OctalObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);
    }
}
