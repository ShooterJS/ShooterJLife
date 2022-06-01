package com.shooterj.java.escape;

import java.util.Optional;

/**
 * Optional规避空指针异常
 */
public class OptionalUsage {

    public static class User {
        private String name;

        public String getName() {
            return name;
        }
    }

    private static User anoymos() {
        return new User();
    }

    public static void main(String[] args) {
        User user = null;
        Optional<User> optionalUser = Optional.ofNullable(user);

        // 存在即返回, 空则提供默认值
        User user1 = optionalUser.orElse(new User());

        // 存在即返回, 空则由函数去产生
        User user2 = optionalUser.orElseGet(() -> anoymos());

        // 存在即返回, 否则抛出异常
//        optionalUser.orElseThrow(RuntimeException::new);

        // 存在才去做相应的处理
        optionalUser.ifPresent(u -> System.out.println(u.getName()));

        // map 可以对 Optional 中的对象执行某种操作, 且会返回一个 Optional 对象
        String anoymos = optionalUser.map(user3 -> user3.getName()).orElse("anoymos");
        System.out.println(anoymos);

        // map 是可以无限级联操作的
        Integer length = optionalUser.map(user3 -> user3.getName()).map(name -> name.length()).orElse(0);


    }
}
