package com.netty.chat.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author seisei
 * @date 2023/9/13
 */
@Data
@AllArgsConstructor
public class LoginReqMessage extends Message{

    private String username;

    private String password;


    @Override
    public int getMesType() {
        return LOGIN_REQUEST_MESSAGE;
    }
}
