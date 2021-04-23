package com.shooterj.core.convert;

import com.shooterj.core.exception.ConvertException;

import java.lang.reflect.Type;

public class Convert {

    /**
     * 转换值为指定类型
     *
     * @param <T> 目标类型
     * @param type 类型
     * @param value 值
     * @return 转换后的值
     * @since 4.0.0
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Class<T> type, Object value) throws ConvertException {
        return convert((Type)type, value);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T> 目标类型
     * @param type 类型
     * @param value 值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Type type, Object value) throws ConvertException{
        return convert(type, value, null);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T> 目标类型
     * @param type 类型
     * @param value 值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Type type, Object value, T defaultValue) throws ConvertException {
        return convertWithCheck(type, value, defaultValue, false);
    }

    /**
     * 转换值为指定类型，可选是否不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param <T> 目标类型
     * @param type 目标类型
     * @param value 值
     * @param defaultValue 默认值
     * @param quietly 是否静默转换，true不抛异常
     * @return 转换后的值
     * @since 5.3.2
     */
    public static <T> T convertWithCheck(Type type, Object value, T defaultValue, boolean quietly) {
        final ConverterRegistry registry = ConverterRegistry.getInstance();
        try {
            return registry.convert(type, value, defaultValue);
        } catch (Exception e) {
            if(quietly){
                return defaultValue;
            }
            throw e;
        }
    }
}
