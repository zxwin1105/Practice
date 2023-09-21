package com.interview.java.Serializability;

import java.io.*;

/**
 * @author seisei
 * @date 2023/7/17
 */
public class Test {

    public static void main(String[] args) throws Exception {
        User user = new User(1, "seisei", 18);
        File file = new File("./obj.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));


        oos.writeObject(user);
        System.out.println(ois.readObject());

    }
}
