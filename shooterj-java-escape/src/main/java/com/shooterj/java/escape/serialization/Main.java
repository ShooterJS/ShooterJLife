package com.shooterj.java.escape.serialization;

import java.io.*;

/**
 * <h1>序列化和反序列化</h1>
 * */
@SuppressWarnings("all")
public class Main {

    /**
     * <h1>序列化和反序列化 People 对象</h1>
     * */
    private static void testSerializablePeople() throws Exception {

        // 序列化的步骤

        // 用于存储序列化的文件
        File file = new File("/tmp/people_10.java_");
        People p = new People(10L);

        // 创建一个输出流
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file)
        );
        // 输出可序列化对象
        oos.writeObject(p);
        // 关闭输出流
        oos.close();

        // 反序列化的步骤

        // 创建一个输入流
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file)
        );
        // 得到反序列化的对象
        Object newPerson = ois.readObject();
        // 关闭输入流
        ois.close();

        System.out.println(newPerson);
    }

    /**
     * <h2>子类实现序列化, 父类不实现序列化</h2>
     * */
    private static void testSerizableWorker() throws Exception {

        File file = new File("/tmp/worker_10.java_");
        Worker p = new Worker(10L, "qinyi", 19);

        // 创建一个输出流
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file)
        );
        // 输出可序列化对象
        oos.writeObject(p);
        // 关闭输出流
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object newWorker = ois.readObject();
        ois.close();
        System.out.println(newWorker);
    }

    private static void testSerializableCombo() throws Exception {

        File file = new File("/tmp/combo_10.java_");
        Combo p = new Combo(1, new People(10L));

        // 创建一个输出流
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file)
        );
        // 输出可序列化对象
        oos.writeObject(p);
        // 关闭输出流
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object newCombo = ois.readObject();
        ois.close();
        System.out.println(newCombo);
    }

    /**
     * <h2>同一个对象多次序列化的问题, 坑</h2>
     * */
    private static void sameObjectRepeatedSerialization() throws Exception {

        File file = new File("/tmp/peopele_more.java_");
        People p = new People(10L);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

        oos.writeObject(p);
        oos.writeObject(p);

        p.setId(20L);
        oos.writeObject(p);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object people1 = ois.readObject();
        Object people2 = ois.readObject();
        Object people3 = ois.readObject();
        ois.close();

        System.out.println(((People) people1).getId());
        System.out.println(((People) people2).getId());
        System.out.println(((People) people3).getId());
    }

    public static void main(String[] args) throws Exception {

//        testSerializablePeople();

//        testSerizableWorker();

        testSerializableCombo();

//        sameObjectRepeatedSerialization();
    }
}
