package com.shooterj.java.escape;

/**
 * <h1>字符串拼接</h1>
 * */
public class StringContact {
    /**
     * javap -l -c -p StringContact.class
     */
    private static void easyContact() {

        String userName = "Qinyi";
        String age = "19";
        String job = "Developer";

        String info = userName + age + job;
        System.out.println(info);
    }

    private static void implicitUseStringBuilder(String[] values) {
        String result = "";

        for (int i = 0; i < values.length; ++i) {
            result += values[i];
        }

        System.out.println(result);
    }

    private static void explicitUseStringBuilder(String[] values) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; ++i) {
            result.append(values[i]);
        }

        System.out.println(result.toString());
    }

    public static void main(String[] args) {

        easyContact();
    }
}
