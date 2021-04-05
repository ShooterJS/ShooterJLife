package com.winway.demo.spring.event;

import com.winway.demo.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserContext {

    public static Map<Integer, User> map = new HashMap<>();

    private UserContext() {
    }

    public static UserContext getInstance() {
        return UserContextholder.instance;
    }

    //静态内部类延迟加载
    private static class UserContextholder {
        private static final UserContext instance = new UserContext();
    }


    public void put(Integer id, User user) {
        map.put(id, user);
    }

    public User get(Integer id) {
        return map.get(id);
    }


}
