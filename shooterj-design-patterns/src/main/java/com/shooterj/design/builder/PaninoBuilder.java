package com.shooterj.design.builder;

import java.util.ArrayList;
import java.util.List;

public class PaninoBuilder {
    private String name;
    private String breadType;
    private String fish;
    private String cheese;
    private String meat;
    private List vegetables=new ArrayList();

    public PaninoBuilder paninoCalled(String name) {
        this.name = name;
        return this;
    }

    public PaninoBuilder breadType(String breadType) {
        this.breadType = breadType;
        return this;
    }

    public PaninoBuilder withFish(String fish) {
        this.fish = fish;
        return this;
    }

    public PaninoBuilder withCheese(String cheese) {
        this.cheese = cheese;
        return this;
    }

    public PaninoBuilder withMeat(String meat) {
        this.meat = meat;
        return this;
    }

    public PaninoBuilder withVegetable(String vegetable) {
        vegetables.add(vegetable);
        return this;
    }

    public Panino build(){
        Panino panino = new Panino(name);
        panino.setBreadType(breadType);
        panino.setCheese(cheese);
        panino.setFish(fish);
        panino.setMeat(meat);
        panino.setVegetables(vegetables);
        return panino;
    }

}
