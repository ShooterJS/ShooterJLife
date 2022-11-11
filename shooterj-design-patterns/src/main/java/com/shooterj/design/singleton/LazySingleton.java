package com.shooterj.design.singleton;

/**
 * 静态内部类，延迟加载
 */
public class LazySingleton {

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

}
