package com.shooterj.design.strategy;

public enum HandlerEnum {

    ONE(1, "one"),
    TWO(2, "two");


    private Integer handlerType;
    private String handlerName;


    HandlerEnum(Integer handlerType, String handlerName) {
        this.handlerType = handlerType;
        this.handlerName = handlerName;
    }

    public Integer getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(Integer handlerType) {
        this.handlerType = handlerType;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
}
