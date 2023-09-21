package com.netty.chat.message;

/**
 * @author seisei
 * @date 2023/9/18
 */
public class PingMessage extends Message {
    @Override
    public int getMesType() {
        return 0;
    }
}
