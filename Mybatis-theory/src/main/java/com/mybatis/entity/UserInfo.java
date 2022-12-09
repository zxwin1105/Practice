package com.mybatis.entity;

import java.io.Serializable;

/**
 * @author zhaixinwei
 * @date 2022/11/17
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 42L;

    private String userId;

    private String userName;

    private Integer userAge;

    private String userGender;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}
