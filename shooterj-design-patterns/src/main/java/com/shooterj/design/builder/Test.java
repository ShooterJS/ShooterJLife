package com.shooterj.design.builder;

public class Test {
    public static void main(String[] args) {
        Panino build = new PaninoBuilder().paninoCalled("marcoPanino").breadType("baguette")
                .withCheese("gorgonzola").withMeat("ham").withVegetable("tomatos").build();

        System.out.println(build);
    }
}
