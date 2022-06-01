package com.shooterj.java.escape.copy_on_write;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("all")
public class GoodListService {

    private static final List<String> goods = new CopyOnWriteArrayList<>();

    public static boolean contains(String good) {
        return goods.contains(good);
    }

    public static void addGood(String good) {
        goods.add(good);
    }

    public static void addGoods(List<String> gds) {
        goods.addAll(gds);
    }
}
