package com.shooterj.java.escape.abstract_interface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h1>部分程序员的工作</h1>
 * */
public interface IExtraWorking {

    void extraCoding();

    void extraTesting();

    default void config() {
        System.out.println("ExtraWorking For Config");
    }

    static void time() {
        System.out.println(
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
        );
    }
}
