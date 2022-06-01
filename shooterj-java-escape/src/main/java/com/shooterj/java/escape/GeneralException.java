package com.shooterj.java.escape;

import com.google.common.base.Enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GeneralException {


    public static class User {
        String name;

        public User(String name) {
            this.name = name;
        }

        public User() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * 1. 并发修改异常
     *
     * @param users
     */
    private static void concurrentModificationException(ArrayList<User> users) {
        // 直接使用 for 循环会触发并发修改异常
        /*for (User user : users) {
            if(user.getName().equals("shooterj")){
                users.remove(user);
            }
        }*/

        Iterator<User> iterator = users.iterator();
        if (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getName().equals("shooterj")) {
                users.remove(user);
            }
        }

    }

    public static Map<String, StaffType> staffTypeMap = new HashMap(StaffType.values().length);
    static {
        for (StaffType value : StaffType.values()) {
            staffTypeMap.put(value.name(),value);
        }
    }

    public static Map<String, TrackingEnum> trackingMap = new HashMap(StaffType.values().length);
    static {
        for (TrackingEnum value : TrackingEnum.values()) {
            trackingMap.put(value.getCode(),value);
        }
    }

    private static StaffType enumFind(String type) {

        // 3. 静态 Map 索引, 只有一次循环枚举的过程
        return staffTypeMap.get(type);

        // 4. 使用 Google Guava Enums, 需要相关的依赖
//        return Enums.getIfPresent(StaffType.class, type).orNull();
    }

    private static TrackingEnum enumFind2(String code) {

        // 3. 静态 Map 索引, 只有一次循环枚举的过程
        return trackingMap.get(code);

    }


    public static void main(String[] args) {
//        List<User> users = Arrays.asList(new User("shooterj"), new User("demo"));
//        ArrayList<User> userArrayList = new ArrayList<>(users);
//        concurrentModificationException(userArrayList);


        System.out.println(enumFind("RD"));

        System.out.println(enumFind("abc"));

        System.out.println(enumFind2(TrackingEnum.INDEX.getCode()).getName());

        System.out.println(enumFind2(TrackingEnum.INDEX.getCode()).getClass());



    }


}
