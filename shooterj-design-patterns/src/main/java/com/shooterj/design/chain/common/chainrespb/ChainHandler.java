package com.shooterj.design.chain.common.chainrespb;

/**
 * 业务：基础验证抽象
 * 设计思想：解耦，单一原则，开闭原则
 * 责任链抽象处理器
 * 行为：check()当前处理器检查，checkNext()下一个处理器检查
 * 属性：下一个处理器
 */
public abstract class ChainHandler {

    private ChainHandler next;

    /**
     * 将链关联
     * @param first    链头
     * @param handlers 子链
     */
    public static ChainHandler link(ChainHandler first, ChainHandler... handlers) {
        ChainHandler head = first;
        for (ChainHandler handler : handlers) {
            head.next = handler;
            head = handler;
        }
        return first;
    }

    /**
     * 模板方法模式：钩子方法
     */
    public abstract boolean check(String email, String password);

    public boolean checkNext(String email, String password) {
        if (next == null) {
            //最后一个节点执行结束，表明责任链通过
            return true;
        }
        return next.check(email, password);
    }


}
