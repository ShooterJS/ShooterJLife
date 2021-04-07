package com.winway.demo.repeat.job;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ShooterJ
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatJob {

    /**
     * 唯一的项目名
     * @return
     */
    String uniqueAppName() default "EIP-RepeatJob";

    /**
     * 过期时间：秒
     * 默认1分钟，考虑各个服务器的时钟不同步
     * @return
     */
    int expire() default 60;

}
