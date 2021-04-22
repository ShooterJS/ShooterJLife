package com.shooterj.core.validator.wwasert;

import com.shooterj.core.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 断言<br>
 * 断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 *
 * @author jason
 */
public class WWAssert {


    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常
     *
     * @param <T>    被检查对象类型
     * @param object 被检查对象
     * @return 非空对象
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static <T> T notNull(T object) throws BizException {
        return notNull(object, "对象不能为空");
    }

    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常 Assert that an object is not {@code null} .
     *
     * @param <T>              被检查对象泛型类型
     * @param object           被检查对象
     * @param errorMsgTemplate 错误消息模板，变量使用this {0} is {1}表示
     * @param params           参数
     * @return 被检查后的对象
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static <T> T notNull(T object, String errorMsgTemplate, Object... params) throws BizException {
        return notNull(object, () -> new BizException(MessageFormat.format(errorMsgTemplate, params)));
    }


    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     *
     * @param <T>           被检查对象泛型类型
     * @param <X>           异常类型
     * @param object        被检查对象
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @return 被检查后的对象
     * @throws X if the object is {@code null}
     * @since 5.4.5
     */
    public static <T, X extends Throwable> T notNull(T object, Supplier<X> errorSupplier) throws X {
        if (object == null) {
            throw errorSupplier.get();
        }
        return object;
    }

    public <T extends CharSequence> T notBlank(T text) {
        return notBlank(text, "不能为空");
    }

    public <T extends CharSequence> T notBlank(T text, String errorMsgTemplate, Object... params) {
        return notBlank(text, () -> new BizException(MessageFormat.format(errorMsgTemplate, params)));
    }


    /**
     * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出自定义异常。
     * 并使用指定的函数获取错误信息返回
     *
     * @param <X>              异常类型
     * @param <T>              字符串类型
     * @param text             被检查字符串
     * @param errorMsgSupplier 错误抛出异常附带的消息生产接口
     * @return 非空字符串
     * @throws X 被检查字符串为空白
     */

    public static <T extends CharSequence, X extends Throwable> T notBlank(T text, Supplier<X> errorMsgSupplier) throws X {
        if (StringUtils.isBlank(text)) {
            throw errorMsgSupplier.get();
        }
        return text;
    }

    public <T> T notEmpty(T obj) {
        return notEmpty(obj, "不能为空");
    }



    /**
     * 检查给定字符串是否为空，为空抛出 {@link IllegalArgumentException}

     * @param <T>              字符串类型
     * @param obj             被检查对象
     * @param errorMsgTemplate 错误消息模板，变量使用this {0} is {1}表示
     * @param params           参数
     * @return 非空字符串
     * @throws IllegalArgumentException 被检查字符串为空
     */
    public static <T> T notEmpty(T obj, String errorMsgTemplate, Object... params) {
        return notEmpty(obj, () -> new BizException(MessageFormat.format(errorMsgTemplate, params)));
    }

    /**
     * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出自定义异常。
     * 并使用指定的函数获取错误信息返回
     *
     * @param <X>              异常类型
     * @param <T>              字符串类型
     * @param obj              被检查字符串，Map,Iterable,Array
     * @param errorMsgSupplier 错误抛出异常附带的消息生产接口
     * @return 非空字符串
     * @throws X 被检查字符串为空白
     */

    public static <T, X extends Throwable> T notEmpty(T obj, Supplier<X> errorMsgSupplier) throws X {
        X x = errorMsgSupplier.get();
        if (obj == null) {
            throw x;
        }
        if (obj instanceof CharSequence) {
            if (StringUtils.isEmpty((CharSequence) obj)) {
                throw x;
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map == null || map.isEmpty()) {
                throw x;
            }
        } else if (obj instanceof Iterable) {
            Iterator iterator = ((Iterable) obj).iterator();
            if (null == iterator || false == iterator.hasNext()) {
                throw x;
            }
        } else if (obj instanceof Iterator) {
            Iterator iterator = (Iterator) obj;
            if (null == iterator || false == iterator.hasNext()) {
                throw x;
            }
        } else if (obj.getClass().isArray()) {
            if (0 == Array.getLength(obj)) {
                throw x;
            }
        }
        return obj;
    }

}
