package com.shooterj.java.escape.try_with_resources;

public class AutoClose implements AutoCloseable {

    @Override
    public void close() {
        System.out.println(">>> close()");
        throw new RuntimeException("Exception in close()");
    }

    public void work() throws MyException {
        System.out.println(">>> work()");
        throw new MyException("Exception in work()");
    }
}
