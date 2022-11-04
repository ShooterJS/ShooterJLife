package com.shooterj.design.stepbuilder;

import com.shooterj.design.builder.Panino;

import java.util.ArrayList;
import java.util.List;

/**
 * The concept is simple:
 * 写创造性步骤内部类或接口中,每个方法都知道接下来可以显示什么
 * 实现所有步骤接口在你的静态内部类中.
 * 最后一步BuildStep，负责创建对象
 * Last step is the BuildStep, in charge of creating the object you need to build.
 */
public class PaninoStepBuilder {


    public PaninoStepBuilder() {
    }

    public static FirstNameStep newBuilder() {
        return new Steps();
    }

    /**
     * 第一步：给Panino起名
     * 下一步 : BreadTypeStep
     */
    public static interface FirstNameStep {
        BreadTypeStep paninoCalled(String name);
    }

    /**
     * 这一步负责决定面包类型
     * 下一步: MainFillingStep
     */
    public static interface BreadTypeStep {
        MainFillingStep breadType(String breadType);
    }

    /**
     * 这一步主要负责设置填充(肉或鱼)
     * 肉选择完下一步:CheeseStep
     * 鱼选择完下一步:VegetableStep
     */
    public static interface MainFillingStep {
        VegetableStep fish(String fish);

        CheeseStep meat(String meat);
    }

    /**
     * 这一步负责填充cheese
     * 下一步 : VegetableStep
     */
    public static interface CheeseStep {
        VegetableStep noCheesePlease();

        VegetableStep withCheese(String cheese);
    }

    /**
     * 这一步负责蔬菜填充
     * 下一步：BuildStep
     */
    public static interface VegetableStep {
        VegetableStep addVegetable(String vegetable);

        BuildStep noMoreVegetablesPlease();

        BuildStep noVegetablesPlease();


    }

    /**
     * 这是最后一步负责建筑Panino对象
     * Validation should be here.
     */
    public static interface BuildStep {
        Panino build();
    }

    private static class Steps implements FirstNameStep, BreadTypeStep, MainFillingStep, CheeseStep, VegetableStep, BuildStep {

        private String name;
        private String breadType;
        private String meat;
        private String fish;
        private String cheese;
        private final List<String> vegetables = new ArrayList<String>();


        @Override
        public BreadTypeStep paninoCalled(String name) {
            this.name = name;
            return this;
        }

        @Override
        public MainFillingStep breadType(String breadType) {
            this.breadType = breadType;
            return this;
        }

        @Override
        public VegetableStep fish(String fish) {
            this.fish = fish;
            return this;
        }

        @Override
        public CheeseStep meat(String meat) {
            this.meat = meat;
            return this;
        }

        @Override
        public VegetableStep noCheesePlease() {
            return this;
        }

        @Override
        public VegetableStep withCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        @Override
        public VegetableStep addVegetable(String vegetable) {
            this.vegetables.add(vegetable);
            return this;
        }

        @Override
        public BuildStep noMoreVegetablesPlease() {
            return this;
        }

        @Override
        public BuildStep noVegetablesPlease() {
            return this;
        }

        @Override
        public Panino build() {
            Panino panino = new Panino(name);
            if (fish == null) {
                panino.setMeat(meat);
            } else {
                panino.setFish(fish);
            }
            if (cheese != null) {
                panino.setCheese(cheese);
            }
            if (!vegetables.isEmpty()) {
                panino.setVegetables(vegetables);
            }
            return panino;
        }
    }


}
