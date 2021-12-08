package com.shooterj.query;


public enum FieldRelation {
    AND("AND"),
    OR("OR");

    private String val;

    FieldRelation(String val) {
        this.val = val;
    }

    public String value() {
        return this.val;
    }
}
