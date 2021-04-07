package com.winway.demo.exception;

/**
 * 数据验证失败的自定义异常。
 *
 * @author ShooterJ
 * @date 2020-08-08
 */
public class DataValidationException extends RuntimeException {

    /**
     * 构造函数。
     */
    public DataValidationException() {

    }

    /**
     * 构造函数。
     *
     * @param msg 错误信息。
     */
    public DataValidationException(String msg) {
        super(msg);
    }
}
