package com.shooterj.java.escape.reflect;

import java.lang.reflect.Method;

/**
 * <h1>Java 中的反射机制</h1>
 * */
public class Main {

    /**
     * <h2>方法的参数是基本类型，反射获取 Method 参数类型必须一致</h2>
     * */
    private static void reflectDeclaredMethod() throws Exception {

        Class<Boss> clz = Boss.class;
        Method[] methods = clz.getDeclaredMethods();

//        Method method = clz.getDeclaredMethod("numeric", int.class);
        Method method = clz.getDeclaredMethod("numeric", Integer.TYPE);
        System.out.println(method.invoke(clz.newInstance(), 19));
    }

    /**
     * <h2>调用的方法属于对象的父类, getDeclaredMethod 会抛出异常</h2>
     * */
    private static void reflectAcquireClassMethod() throws Exception {

        Class<Boss> clz = Boss.class;
//        Method method = clz.getDeclaredMethod("boss", String.class);
//        Method method = clz.getDeclaredMethod("worker", String.class);

        Method superMethod = getMethod(clz, "worker",
                new Class[]{String.class});
        if (superMethod != null) {
            System.out.println(superMethod.invoke(clz.newInstance(), "boss"));
        }
    }

    private static Method getMethod(Class<?> target, String methodName,
                                    Class<?>[] argTypes) {

        Method method = null;

        try {
            method = target.getDeclaredMethod(methodName, argTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException ex) {
            System.out.println("can not get method: " + methodName + " from "
                    + target.getName());
        }
        if (method == null && target != Object.class) {
            return getMethod(target.getSuperclass(), methodName, argTypes);
        }

        return method;
    }

    public static void main(String[] args) throws Exception {

        reflectDeclaredMethod();

//        reflectAcquireClassMethod();
    }
}
