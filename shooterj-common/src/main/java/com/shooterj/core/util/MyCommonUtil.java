package com.shooterj.core.util;

import com.shooterj.core.exception.BizException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * 脚手架中常用的基本工具方法集合，一般而言工程内部使用的方法。
 *
 * @author ShooterJ
 * @date 2020-08-08
 */
public class MyCommonUtil {

    private static final Validator VALIDATOR;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 判断模型对象是否通过校验，没有通过返回具体的校验错误信息。
     *
     * @param model  带校验的model。
     * @param groups Validate绑定的校验组。
     * @return 没有错误返回null，否则返回具体的错误信息。
     */
    public static <T> String getModelValidationError(T model, Class<?>...groups) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(model, groups);
        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
            ConstraintViolation<T> constraint = it.next();
            return constraint.getMessage();
        }
        return null;
    }


    /**
     * 校验对象
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     */
    public static <T> void validateAndThroException(T bean, Class<?>... groups) {
        warpBeanValidationResult(validate(bean, groups));
    }

    /**
     * 校验对象
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link Set}
     */
    public static <T> Set<ConstraintViolation<T>> validate(T bean, Class<?>... groups) {
        return VALIDATOR.validate(bean, groups);
    }

    /**
     * 包装校验结果
     *
     * @param constraintViolations 校验结果集
     */
    private static <T> void warpBeanValidationResult(Set<ConstraintViolation<T>> constraintViolations) {
        boolean state = constraintViolations.isEmpty();
        if(!state){
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                throw new BizException(constraintViolation.getMessage());
            }
        }
    }

    /**
     * 创建uuid。
     *
     * @return 返回uuid。
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
