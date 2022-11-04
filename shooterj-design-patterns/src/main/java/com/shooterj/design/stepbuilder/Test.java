package com.shooterj.design.stepbuilder;

import com.shooterj.design.builder.Panino;


public class Test {

    public static void main(String[] args) {
        Panino solePanino = PaninoStepBuilder.newBuilder()
                .paninoCalled("sole panino")
                .breadType("baguette")
                .fish("sole")
                .addVegetable("tomato")
                .addVegetable("lettece")
                .noMoreVegetablesPlease()
                .build();
        System.out.println(solePanino);
    }
}
