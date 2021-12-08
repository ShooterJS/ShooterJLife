package com.shooterj.query;

public enum Direction {
    ASC,
    DESC;

    public static Direction fromString(String value) {
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return ASC;
        }
    }
}
