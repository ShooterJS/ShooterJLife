package com.shooterj.java.escape.serialization;

import lombok.ToString;

import java.io.Serializable;

/**
 * <h1>Java Object</h1>
 * */
@ToString
public class Worker extends People implements Serializable {

    private String name;
    private Integer age;

    public Worker(Long id, String name, Integer age) {
        super(id);
        this.name = name;
        this.age = age;
    }
}
