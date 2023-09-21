package com.netty.chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author seisei
 * @date 2023/9/13
 */
@Data
@AllArgsConstructor
public class ChatReqMessage extends Message{

    /** 发送消息的用户 */
    private String from;

    /** 接收消息的用户 */
    private String to;

    /** 消息内容 */
    private String msg;

    @Override
    public int getMesType() {
        return CHAT_REQUEST_MESSAGE;
    }
}
