package com.pay.exception;

/**
 * @author: zhaixinwei
 * @date: 2022/6/19
 * @description:
 */
public class ServiceException extends RuntimeException{
    private int code;
    private String msg;

    public ServiceException(int code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
