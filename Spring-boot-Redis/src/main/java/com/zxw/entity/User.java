package com.zxw.entity;

import lombok.Data;

/**
 * @author: zhaixinwei
 * @date: 2022/5/12
 * @description:
 */
@Data
public class User {
    private int id;
    private String name;
    private String gender;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
