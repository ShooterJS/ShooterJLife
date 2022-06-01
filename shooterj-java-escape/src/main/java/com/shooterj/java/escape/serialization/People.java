package com.shooterj.java.escape.serialization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <h1>Java Object</h1>
 * */
@Setter
@Getter
@ToString
public class People implements Serializable {

    private Long id;

    public People() {}

    public People(Long id) {
        this.id = id;
    }
}
