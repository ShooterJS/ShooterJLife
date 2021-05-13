package com.shooterj.core.model;


public class User {
    private Integer id;
    private Integer age;
    private String name;
    private Integer deviceType;

    public User() {
    }

    public User(Integer id, Integer age, String name, Integer deviceType) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.deviceType = deviceType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
}
