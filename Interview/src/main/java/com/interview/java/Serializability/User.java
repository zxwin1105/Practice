package com.interview.java.Serializability;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author seisei
 * @date 2023/7/17
 */
public class User implements Serializable {
    private int id;

    private String name;

    private int age;

    public User() {
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    private Object writeReplace() throws ObjectStreamException {
        System.out.println("dddd");
        return new User(2,"zxwin",19);
    }

}
