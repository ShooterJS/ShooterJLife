package com.shooterj.annotation;

import com.shooterj.enums.EncryptConstant;

import java.lang.annotation.*;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/8/12
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptMethod {

    String type() default EncryptConstant.ENCRYPT;
}
