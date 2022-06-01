package com.shooterj.java.escape.synchronized_;

public class Main {

    private  int value = 0;

    public static void main(String[] args) {

        MainActive active = new MainActive();

        Thread threa1 = new Thread(active, "threa1");
        Thread threa2 = new Thread(active, "threa2");
        Thread threa3 = new Thread(active, "threa3");
        Thread threa4 = new Thread(active, "threa4");
        Thread threa5 = new Thread(active, "threa5");

        threa1.start();
        threa2.start();
        threa3.start();
        threa4.start();
        threa5.start();
    }

}
