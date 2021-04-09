package com.winway.demo.design.observer;

public class OctalObserver extends WWObserver {


    public OctalObserver(WWSubject subject) {
        this.subject = subject;
        this.subject.register(this);
    }

    @Override
    public void update(){
        System.out.println("OctalObserver 的主题对象是"+subject.toString());
        System.out.println( "Octal String: " + Integer.toOctalString( subject.getState() ) );
    }
}
