package com.shooterj.design.observer;


public class HexaObserver extends WWObserver {


    public HexaObserver(WWSubject subject) {
        this.subject = subject;
        //注册
        this.subject.register(this);
    }


    @Override
    public void update() {
        System.out.println("HexaObserver 的主题对象是"+subject.toString());
        System.out.println("Hex String: "
                + Integer.toHexString(subject.getState()).toUpperCase());
    }

    public static void main(String[] args) {
        System.out.println(Integer.toHexString(10).toUpperCase());
    }
}
