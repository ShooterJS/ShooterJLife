package com.shooterj.java.escape;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>理解泛型</h1>
 * */
@SuppressWarnings("all")
public class Genericity {

    /**
     * <h2>简单使用泛型</h2>
     * */
    private static void easyUse() throws Exception {

        List<String> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

//        System.out.println(left.getClass());
//        System.out.println(left.getClass() == right.getClass());

//        if (left instanceof ArrayList<Double>) {}
//        if (left instanceof ArrayList) {
//
//        }
//
//        if (left instanceof ArrayList<?>) {}

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.getClass().getMethod("add", Object.class).invoke(list, "abcd");
        list.getClass().getMethod("add", Object.class).invoke(list, 1.2);

        for (int i = 0; i != list.size(); ++i) {
            System.out.println(list.get(i));
        }
    }

    /**
     * <h2>泛型是先检查再编译</h2>
     * */
    private static void checkAndCompile() {

        ArrayList<String> list = new ArrayList<>();
        list.add("1234");
//        list.add(123);
    }

    /**
     * <h2>泛型不支持继承</h2>
     * */
    private static void genericityCanNotExtend() {

        // 第一类错误
//        ArrayList<String> first = new ArrayList<Object>();
//
//        ArrayList<Object> list1 = new ArrayList<>();
//        list1.add(new Object());
//        ArrayList<String> list2 = list1;

        // 第二类错误
//        ArrayList<Object> second = new ArrayList<String>();
//
//        ArrayList<String> list1 = new ArrayList<>();
//        list1.add(new String());
//        ArrayList<Object> list2 = list1;
    }

    /**
     * <h2>泛型类型变量不能是基本数据类型</h2>
     * */
    private static void baseTypeCanNotUseGenericity() {

//        List<int> invalid = new ArrayList<>();
    }

    /**
     * <h2>泛型的类型参数只能是类类型, 不能是简单类型</h2>
     * */
    private static <T> void doSomething(T... values) {
        for (T value : values) {
            System.out.println(value);
        }
    }

    public static void main(String[] args) throws Exception {

//        easyUse();

        Integer[] ints1 = new Integer[]{1, 2, 3};
        int[] ints2 = new int[]{1, 2, 3};

        doSomething(ints1);
        System.out.println("----------------");
        doSomething(ints2);
    }
}
