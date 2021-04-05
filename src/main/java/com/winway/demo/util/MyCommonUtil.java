package com.winway.demo.util;

import java.util.UUID;

/**
 * 脚手架中常用的基本工具方法集合，一般而言工程内部使用的方法。
 *
 * @author Jerry
 * @date 2020-08-08
 */
public class MyCommonUtil {
    /**
     * 创建uuid。
     *
     * @return 返回uuid。
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
