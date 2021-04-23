package com.shooterj.core.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class User {
    private Integer id;
    private Integer age;
    private String name;
    private Integer deviceType;

}
