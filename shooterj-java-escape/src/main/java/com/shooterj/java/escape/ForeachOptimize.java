package com.shooterj.java.escape;

import java.util.Arrays;
import java.util.Collection;

/**
 * <h1>小小 for 循环, 沾上集合出大问题</h1>
 * */
@SuppressWarnings("all")
public class ForeachOptimize {

    private static Collection<Integer> left =
            Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    private static Collection<Integer> right =
            Arrays.asList(1, 2, 3, 4, 5);

    /**
     * <h2>集合迭代经常犯的错误</h2>
     * */
    private static void wrongIterator() {

//        // 传统方式 - 使用索引
//        int[] xyz = new int[]{1, 2, 3, 4, 5};
//        for (int i = 0; i != xyz.length; ++i) {
//            System.out.println(xyz[i]);
//        }
//
//        // 传统方式 - 迭代器
//        for (Iterator<Integer> i = left.iterator(); i.hasNext(); ) {
//            System.out.println(i.next());
//        }

        // 嵌套迭代容易出现问题
//        for (Iterator<Integer> l = left.iterator(); l.hasNext(); ) {
//            for (Iterator<Integer> r = right.iterator(); r.hasNext(); ) {
//                System.out.println(l.next() * r.next());
//            }
//        }

        // 正确的用法, 嵌套迭代
//        for (Iterator<Integer> l = left.iterator(); l.hasNext(); ) {
//            Integer tmp = l.next();
//            for (Iterator<Integer> r = right.iterator(); r.hasNext(); ) {
//                System.out.println(tmp * r.next());
//            }
//        }

        for (Integer l : left) {
            for (Integer r : right) {
                System.out.println(l * r);
            }
        }
    }

    private static void square(int value) {
        System.out.println(value * value);
    }

    public static void main(String[] args) {

        wrongIterator();


        for (Integer l : left) {
            square(l);
        }

        // Java8 Iterable.forEach vs for-each
        left.forEach(l -> square(l));
        left.forEach(ForeachOptimize::square);
    }
}
