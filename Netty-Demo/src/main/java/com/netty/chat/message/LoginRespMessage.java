package com.netty.chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author seisei
 * @date 2023/9/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRespMessage extends Message{

    private Boolean flag;
    private String msg;
    @Override
    public int getMesType() {
        return LOGIN_RESPONSE_MESSAGE;
    }
}
