package com.shooterj.design.reflectAndAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface BankAPIField {
    int order() default -1;

    int length() default -1;

    String type() default "";
}