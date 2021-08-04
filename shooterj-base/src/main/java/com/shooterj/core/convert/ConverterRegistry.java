package com.shooterj.core.convert;

import com.shooterj.core.exception.ConvertException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 转换器登记中心
 * <p>
 * 将各种类型Convert对象放入登记中心，通过convert方法查找目标类型对应的转换器，将被转换对象转换之。
 * </p>
 * <p>
 * 在此类中，存放着默认转换器和自定义转换器，默认转换器是Hutool中预定义的一些转换器，自定义转换器存放用户自定的转换器。
 * </p>
 *
 * @author shooterj
 */
public class ConverterRegistry implements Serializable {

    /**
     * 默认类型转换器
     */
    private Map<Type, Converter<?>> defaultConverterMap;
    /**
     * 用户自定义类型转换器
     */
    private volatile Map<Type, Converter<?>> customConverterMap;
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static final ConverterRegistry INSTANCE = new ConverterRegistry();
    }

    /**
     * 获得单例的 ConverterRegistry
     *
     * @return ConverterRegistry
     */
    public static ConverterRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 转换值为指定类型<br>
     * 自定义转换器优先
     *
     * @param <T>          转换的目标类型（转换器转换到的类型）
     * @param type         类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public <T> T convert(Type type, Object value, T defaultValue) throws ConvertException {
        return convert(type, value, defaultValue, true);
    }


    /**
     * 转换值为指定类型
     *
     * @param <T>           转换的目标类型（转换器转换到的类型）
     * @param type          类型目标
     * @param value         被转换值
     * @param defaultValue  默认值
     * @param isCustomFirst 是否自定义转换器优先
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Type type, Object value, T defaultValue, boolean isCustomFirst) throws ConvertException {/*
        if (isUnknown(type) && null == defaultValue) {
            // 对于用户不指定目标类型的情况，返回原值
            return (T) value;
        }
        if (WWObjectUtil.isNull(value)) {
            return defaultValue;
        }
        if (isUnknown(type)) {
            type = defaultValue.getClass();
        }

        if (type instanceof TypeReference) {
            type = ((TypeReference<?>) type).getType();
        }

        // 标准转换器
        final Converter<T> converter = getConverter(type, isCustomFirst);
        if (null != converter) {
            return converter.convert(value, defaultValue);
        }

        Class<T> rowType = (Class<T>) TypeUtil.getClass(type);
        if (null == rowType) {
            if (null != defaultValue) {
                rowType = (Class<T>) defaultValue.getClass();
            } else {
                // 无法识别的泛型类型，按照Object处理
                return (T) value;
            }
        }


        // 特殊类型转换，包括Collection、Map、强转、Array等
        final T result = convertSpecial(type, rowType, value, defaultValue);
        if (null != result) {
            return result;
        }

        // 尝试转Bean
        if (BeanUtil.isBean(rowType)) {
            return new BeanConverter<T>(type).convert(value, defaultValue);
        }

        // 无法转换
        throw new ConvertException("Can not Converter from [{}] to [{}]", value.getClass().getName(), type.getTypeName());*/
        return null;
    }


    /**
     * 获得转换器<br>
     *
     * @param <T>           转换的目标类型
     * @param type          类型
     * @param isCustomFirst 是否自定义转换器优先
     * @return 转换器
     */
    public <T> Converter<T> getConverter(Type type, boolean isCustomFirst) {
        Converter<T> converter;
        if (isCustomFirst) {
            converter = this.getCustomConverter(type);
            if (null == converter) {
                converter = this.getDefaultConverter(type);
            }
        } else {
            converter = this.getDefaultConverter(type);
            if (null == converter) {
                converter = this.getCustomConverter(type);
            }
        }
        return converter;
    }

    /**
     * 获得默认转换器
     *
     * @param <T>  转换的目标类型（转换器转换到的类型）
     * @param type 类型
     * @return 转换器
     */
    @SuppressWarnings("unchecked")
    public <T> Converter<T> getDefaultConverter(Type type) {
        return (null == defaultConverterMap) ? null : (Converter<T>) defaultConverterMap.get(type);
    }

    /**
     * 获得自定义转换器
     *
     * @param <T>  转换的目标类型（转换器转换到的类型）
     * @param type 类型
     * @return 转换器
     */
    @SuppressWarnings("unchecked")
    public <T> Converter<T> getCustomConverter(Type type) {
        return (null == customConverterMap) ? null : (Converter<T>) customConverterMap.get(type);
    }

    /**
     * 特殊类型转换<br>
     * 包括：
     *
     * <pre>
     * Collection
     * Map
     * 强转（无需转换）
     * 数组
     * </pre>
     *
     * @param <T>          转换的目标类型（转换器转换到的类型）
     * @param type         类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    @SuppressWarnings("unchecked")
    private <T> T convertSpecial(Type type, Class<T> rowType, Object value, T defaultValue) {/*
        if (null == rowType) {
            return null;
        }

        // 集合转换（不可以默认强转）
        if (Collection.class.isAssignableFrom(rowType)) {
            final CollectionConverter collectionConverter = new CollectionConverter(type);
            return (T) collectionConverter.convert(value, (Collection<?>) defaultValue);
        }

        // Map类型（不可以默认强转）
        if (Map.class.isAssignableFrom(rowType)) {
            final MapConverter mapConverter = new MapConverter(type);
            return (T) mapConverter.convert(value, (Map<?, ?>) defaultValue);
        }

        // 默认强转
        if (rowType.isInstance(value)) {
            return (T) value;
        }

        // 枚举转换
        if (rowType.isEnum()) {
            return (T) new EnumConverter(rowType).convert(value, defaultValue);
        }

        // 数组转换
        if (rowType.isArray()) {
            final ArrayConverter arrayConverter = new ArrayConverter(rowType);
            return (T) arrayConverter.convert(value, defaultValue);
        }*/

        // 表示非需要特殊转换的对象
        return null;
    }
}
