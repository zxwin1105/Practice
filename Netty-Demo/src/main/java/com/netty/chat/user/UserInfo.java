package com.netty.chat.user;

import com.netty.chat.message.LoginReqMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author seisei
 * @date 2023/9/16
 */
public class UserInfo {


    /** 用户信息集合 */
    private static Map<String,String> USER_MAPS = new HashMap<>();

    static {
        USER_MAPS.put("zhangsan","123");
        USER_MAPS.put("lisi","123");
        USER_MAPS.put("wangwu","123");
        USER_MAPS.put("zhaoliu","123");
    }

    public static boolean login(LoginReqMessage message){
        String username = message.getUsername();
        String password = message.getPassword();
        if(password == null || "".equals(password)){
            return false;
        }
        return password.equals(USER_MAPS.get(username));
    }
}
