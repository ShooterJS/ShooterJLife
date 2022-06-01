package com.shooterj.java.escape;


/**
 * <h1>Java 异常处理</h1>
 * */
@SuppressWarnings("all")
public class ExceptionProcess {

    private static class User {}

    /**
     * <h2>Java 异常本质 -- 抛出异常</h2>
     * */
    private void throwException() {

        User user = null;
        // ....
        if (null == user) {
            throw new NullPointerException();
        }
    }

    /**
     * <h2>不能捕获空指针异常</h2>
     * */
    private void canNotCatchNpeException() {

        try {
            throwException();
        } catch (ClassCastException cce) {
            System.out.println(cce.getMessage());
            System.out.println(cce.getClass().getName());
        }
    }

    /**
     * <h2>能够捕获空指针异常</h2>
     * */
    private void canCatchNpeException() {

        try {
            throwException();
        } catch (ClassCastException cce) {
            System.out.println(cce.getMessage());
            System.out.println(cce.getClass().getName());
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
            System.out.println(npe.getClass().getName());
        }
    }

    public static void main(String[] args) {

        ExceptionProcess process = new ExceptionProcess();
        process.canCatchNpeException();
        System.out.println("程序不会终止");
//        process.canNotCatchNpeException();
    }
}


