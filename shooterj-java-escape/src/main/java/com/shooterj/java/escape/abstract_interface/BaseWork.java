package com.shooterj.java.escape.abstract_interface;

/**
 * <h1>每一个 Worker 最基本的属性</h1>
 * */
public abstract class BaseWork {

    /** 起床时间 */
    protected int wakeupTime = 8;

    /** 上班打卡 */
    protected abstract void clockIn();

    /** 下班打卡 */
    protected abstract void clockOut();
}
