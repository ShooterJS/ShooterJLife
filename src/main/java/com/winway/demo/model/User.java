package com.winway.demo.model;

import com.winway.demo.validator.customer.TextLength;
import com.winway.demo.validator.group.UpdateGroup;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder

public class User {
    @NotNull(message = "id不能为空",groups = UpdateGroup.class)
    private Integer id;
    private Integer age;
    @TextLength(min = 2,max = 5)
    private String name;

    public User() {
    }

    public User(Integer id, Integer age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
}
