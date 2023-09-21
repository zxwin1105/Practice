package com.netty.chat.message;

import java.io.Serializable;

/**
 * 消息的抽象
 * @author seisei
 * @date 2023/9/13
 */
public abstract class Message implements Serializable {

    /**
     * 获取消息类型，各子类是实现，返回子类真实的消息类型
     * @return 消息类型
     */
    public abstract int getMesType();

    // 定义不同消息类型
    /** 登录请求消息 */
    public static final Integer LOGIN_REQUEST_MESSAGE = 1;
    /** 登录响应消息 */
    public static final Integer LOGIN_RESPONSE_MESSAGE = 2;

    /** 会话请求消息 */
    public static final Integer CHAT_REQUEST_MESSAGE = 3;
    /** 会话响应消息 */
    public static final Integer CHAT_RESPONSE_MESSAGE = 4;
}
