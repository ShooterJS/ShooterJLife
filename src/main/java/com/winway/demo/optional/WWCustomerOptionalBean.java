package com.winway.demo.optional;


import com.winway.demo.util.WWObjectUtil;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ofNullable包装一个可能为空的对象
 * getBean 取出一个可能为空的对象
 * get：得到全局返回值
 */
public class WWCustomerOptionalBean<T> {

    private T value;

    private static final WWCustomerOptionalBean EMPTY = new WWCustomerOptionalBean();

    public WWCustomerOptionalBean() {
        this.value = null;
    }

    /**
     * 空值会抛出空指针
     *
     * @param value Bean值
     */
    public WWCustomerOptionalBean(T value) {
        this.value = Objects.requireNonNull(value);
    }

    private static <T> WWCustomerOptionalBean<T> empty() {
        WWCustomerOptionalBean<T> none = EMPTY;
        return none;
    }

    public static <T> WWCustomerOptionalBean<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 包装有个不为空的对象
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> WWCustomerOptionalBean<T> of(T value) {
        return new WWCustomerOptionalBean<>(value);
    }




    public <R> WWCustomerOptionalBean<R> getBean(Function<? super T, ? extends R> fn) {
        return value == null ? empty() : WWCustomerOptionalBean.ofNullable(fn.apply(value));
    }

    public T get() {
        return value;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public T orElse(T other) {
        return WWObjectUtil.defaultIfNull(this.value, other);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> supplier) {
        if (value == null) {
            supplier.get();
        }
        return value;
    }

    /**
     * 如果目标值为空 通过lambda表达式获取一个值
     *
     * @param other 默认值函数
     * @return 空返回默认值函数获得的值，否则返回原值
     */
    public T orElseGet(Supplier<? extends T> other) {
        return value == null ? other.get() : value;
    }


    public static  void validateAndApplyFunction(boolean flag, Function<Object[], Object> trueFunc, Function<Object[], Object> falseFunc, Object...args){
        if(flag){
            trueFunc.apply(args);
        }else{
            falseFunc.apply(args);
        }
    }

}
