package com.zxw;

/**
 * @author zhaixinwei
 * @date 2023/1/17
 */
public enum ResEnum {
    UNAUTHORIZED(401,"无权限"),

    PARAMS_FAIL(400,"缺少参数"),

    METHOD_NOT_ALLOWED(405, "方法不允许");

    private int code;
    private String msg;

    ResEnum(int code, String msg) {
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
