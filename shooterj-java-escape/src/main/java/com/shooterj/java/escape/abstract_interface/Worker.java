package com.shooterj.java.escape.abstract_interface;

/**
 * <h1>员工类</h1>
 * */
public class Worker extends BaseWork implements IBaseWorking, IExtraWorking {

    @Override
    protected void clockIn() {

    }

    @Override
    protected void clockOut() {

    }

    @Override
    public void baseCoding() {

    }

    @Override
    public void baseTesting() {

    }

    @Override
    public void config() {
        // 定义自己的 config
    }

    @Override
    public void extraCoding() {

    }

    @Override
    public void extraTesting() {

    }
}
