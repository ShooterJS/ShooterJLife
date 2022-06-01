package com.shooterj.java.escape.reflect;

/**
 * <h1>People 对象</h1>
 * */
public class People {

    public String people(String hello) {
        return People.class.getName() + ": " + hello;
    }
}
