package com.shooterj.query;

import java.io.Serializable;
import java.util.regex.Pattern;


public class FieldSort implements Serializable {
    private static final long serialVersionUID = -1712830705595375365L;
    private static String INJECTION_REGEX = "[A-Za-z0-9\\_\\-\\+\\.]+";

    private Direction direction;
    private String property;

    public FieldSort() {}

    public FieldSort(String property) {
        this(property, Direction.ASC);
    }

    public FieldSort(String property, Direction direction) {
        this.direction = direction;
        this.property = property;
    }

    public static FieldSort create(String property, String direction) {
        return new FieldSort(property, Direction.fromString(direction));
    }

    public Direction getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setProperty(String property) {
        this.property = property;
    }


    public static boolean isSQLInjection(String str) {
        return !Pattern.matches(INJECTION_REGEX, str);
    }


    public String toSql(Class<?> clazz) {
        if (isSQLInjection(property)) {
            throw new IllegalArgumentException("SQLInjection property: " + property);
        }
        // 将实体类属性转换为数据库列名
        property = FieldConvertUtil.property2Field(property, clazz);
        return property + (direction == null ? "" : " " + direction.name());
    }
}