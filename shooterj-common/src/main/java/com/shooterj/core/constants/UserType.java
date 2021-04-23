package com.shooterj.core.constants;

import java.util.HashMap;
import java.util.Map;

public class UserType {


    public static Integer STUDENT = 1;

    public static Integer SPECIALIST = 2;

    private static final Map<Object, String> DICT_MAP = new HashMap<>(3);

    static {
        DICT_MAP.put(STUDENT, "学生");
        DICT_MAP.put(SPECIALIST, "专家");
    }

    /**
     * 判断参数是否为当前常量字典的合法值。
     *
     * @param value 待验证的参数值。
     * @return 合法返回true，否则false。
     */
    public static boolean isValid(Integer value) {
        return value != null && DICT_MAP.containsKey(value);
    }

    /**
     * 私有构造函数，明确标识该常量类的作用。
     */
    private UserType() {
    }
}
