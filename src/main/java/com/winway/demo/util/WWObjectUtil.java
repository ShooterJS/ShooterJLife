package com.winway.demo.util;


import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Supplier;

public class WWObjectUtil {
    /**
     * 如果给定对象为{@code null}返回默认值
     *
     * <pre>
     * ObjectUtil.defaultIfNull(null, null)      = null
     * ObjectUtil.defaultIfNull(null, "")        = ""
     * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtil.defaultIfNull("abc", *)        = "abc"
     * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param <T>          对象类型
     * @param object       被检查对象，可能为{@code null}
     * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
     * @return 被检查对象为{@code null}返回默认值，否则返回原值
     * @since 3.0.7
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return (null != object) ? object : defaultValue;
    }


    /**
     * 如果给定对象为{@code null} 返回默认值, 如果不为null 返回自定义handle处理后的返回值
     *
     * @param source       Object 类型对象
     * @param handle       自定义的处理方法
     * @param defaultValue 默认为空的返回值
     * @param <T>          被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
     * @return 处理后的返回值
     * @since 5.4.6
     */
    public static <T> T defaultIfNull(Object source, Supplier<? extends T> handle, final T defaultValue) {
        if (Objects.nonNull(source)) {
            return handle.get();
        }
        return defaultValue;
    }

    /**
     * 如果给定对象为{@code null}或者""返回默认值, 否则返回自定义handle处理后的返回值
     *
     * @param str          String 类型
     * @param handle       自定义的处理方法
     * @param defaultValue 默认为空的返回值
     * @param <T>          被检查对象为{@code null}或者 ""返回默认值，否则返回自定义handle处理后的返回值
     * @return 处理后的返回值
     * @since 5.4.6
     */
    public static <T> T defaultIfEmpty(String str, Supplier<? extends T> handle, final T defaultValue) {
        if (StringUtils.isNotEmpty(str)) {
            return handle.get();
        }
        return defaultValue;
    }
}
