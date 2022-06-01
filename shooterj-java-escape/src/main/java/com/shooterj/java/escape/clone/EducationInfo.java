package com.shooterj.java.escape.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <h1>教育信息</h1>
 * */
@Data
@AllArgsConstructor
public class EducationInfo implements /*Cloneable*/ Serializable {

    private String school;

    private String time;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
