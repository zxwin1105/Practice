package com.guava.entity;

/**
 * @author zxwin
 * @date 2022/9/17
 */
public class User {
    private String id;

    private String name;

    private String gender;

    public User() {
    }

    public User(String id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
