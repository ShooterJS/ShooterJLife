package com.shooterj.design.builder;

import java.util.List;

public class Panino {
    private final String name;
    private String breadType;
    private String fish;
    private String cheese;
    private String meat;
    private List vegetables;

    public Panino(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBreadType() {
        return breadType;
    }

    public void setBreadType(String breadType) {
        this.breadType = breadType;
    }

    public String getFish() {
        return fish;
    }

    public void setFish(String fish) {
        this.fish = fish;
    }

    public String getCheese() {
        return cheese;
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }

    public List getVegetables() {
        return vegetables;
    }

    public void setVegetables(List vegetables) {
        this.vegetables = vegetables;
    }

    @Override
    public String toString() {
        return "Panino{" +
                "name='" + name + '\'' +
                ", breadType='" + breadType + '\'' +
                ", fish='" + fish + '\'' +
                ", cheese='" + cheese + '\'' +
                ", meat='" + meat + '\'' +
                ", vegetables=" + vegetables +
                '}';
    }
}
