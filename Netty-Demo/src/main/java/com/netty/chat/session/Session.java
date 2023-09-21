package com.netty.chat.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session定义通过 username->channel组成
 * 要确保Session唯一对象
 * @author seisei
 * @date 2023/9/13
 */
public class Session {

    /** 存储Session的Map */
    private final Map<String, Channel> sessionMap = new ConcurrentHashMap<>();

    public static final Session INSTANCE = new Session();
    private Session(){

    }

    public static Session build(){
        return INSTANCE;
    }

    /**
     * 存储session
     * @param username 用户名
     * @param channel channel
     * @return 是否报错成功
     */
    public boolean save(String username, Channel channel){
        return sessionMap.putIfAbsent(username, channel) == null;
    }

    public Channel getChannel(String username){
        return sessionMap.get(username);
    }

    public Channel removeChannel(String username){
        return sessionMap.remove(username);
    }

    public boolean inbound(Channel channel) {
        return sessionMap.values().remove(channel);
    }
}
