package com.shooterj.java.escape;

import java.util.*;

/**
 * <h1>如果不好好判等, 集合存储就会乱套</h1>
 * */
public class EqualOrElse {

    public static class User implements Comparable<User> {

        private String name;
        private Integer age;

        public User() {}

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj instanceof User) {
                User user = (User) obj;
                return this.name.equals(user.name) && this.age == user.age;
            }

            return false;
        }

        @Override
        public int hashCode() {

            int result = name.hashCode();
            result = 31 * result + age;

            return result;
        }

        @Override
        public int compareTo(User o) {
            return (this.age - o.age) + this.name.compareTo(o.name);
        }
    }

    /**
     * <h2>实现/不实现 equals 方法和 hashcode 对于判等的影响</h2>
     * */
    private static void equalsAndHashcode() {

        User user1 = new User("qinyi", 19);
        User user2 = new User("qinyi", 19);

//        System.out.println(user1.equals(user2));

        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        userSet.add(user2);

        Map<User, Integer> userIntegerMap = new HashMap<>();
        userIntegerMap.put(user1, 0);
        userIntegerMap.put(user2, 0);

        System.out.println(userSet.size());
        System.out.println(userIntegerMap.size());
    }

    /**
     * <h2>集合元素索引与 equals 方法相关</h2>
     * */
    private static void compareToAndEquals() {

        List<User> users = new ArrayList<>();
        users.add(new User("qinyi", 10));
        users.add(new User("qinyi", 20));

        User user = new User("qinyi", 20);

        int index1 = users.indexOf(user);
        int index2 = Collections.binarySearch(users, user);

        System.out.println(index1);
        System.out.println(index2);
    }

    public static void main(String[] args) {
//        equalsAndHashcode();
        compareToAndEquals();

    }
}
