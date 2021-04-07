package com.winway.demo.design.observer;

import javax.security.auth.Subject;

public class HexaObserver extends WWObserver {

    WWSubject subject;

    public HexaObserver(WWSubject subject) {
        this.subject = subject;
        //注册
        this.subject.register(this);
    }


    @Override
    public void update() {
        System.out.println("Hex String: "
                + Integer.toHexString(subject.getState()).toUpperCase());
    }

}
