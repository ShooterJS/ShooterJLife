package com.shooterj.java.escape.synchronized_;

public class MainActive implements Runnable{

    private int value=0;

    @Override
    public synchronized void run() {
        String name =Thread.currentThread().getName();
        while(true){
            if(value<1000){
                System.out.println(name +" start : "+value);
                value++;
                System.out.println(name +" done : "+value);
            }else{
                break;
            }
        }
    }
}
