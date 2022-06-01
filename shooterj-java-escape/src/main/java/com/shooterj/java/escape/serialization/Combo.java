package com.shooterj.java.escape.serialization;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <h1>类中存在引用对象</h1>
 * */
@Getter
@Setter
public class Combo implements Serializable {

    private int id;
    private People people;

    public Combo(int id, People people) {
        this.id = id;
        this.people = people;
    }
}
