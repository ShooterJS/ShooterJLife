package com.shooterj.core.validator;

import com.shooterj.core.util.ReflectUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 数据字段自定义验证，用于验证Model中字符串字段的最大长度和最小长度。
 *
 * @author shooterJ
 */
public class ConstDictValidator implements ConstraintValidator<ConstDictRef, Object> {

    private ConstDictRef constDictRef;

    @Override
    public void initialize(ConstDictRef constraintAnnotation) {
        this.constDictRef = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Method method = ReflectUtil.getMethodByName(constDictRef.constDictClass(), "isValid");
        try {
            return (Boolean) method.invoke(null,(Object) value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
//        return ReflectUtil.invokeStatic(method, value);
    }

}