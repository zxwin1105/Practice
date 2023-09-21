package com.netty.chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author seisei
 * @date 2023/9/13
 */
@Data
@AllArgsConstructor
public class ChatRespMessage extends Message{

    private Boolean flag;

    private String msg;
    @Override
    public int getMesType() {
        return CHAT_RESPONSE_MESSAGE;
    }
}
