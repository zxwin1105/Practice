package com.zxw;

/**
 * @author zhaixinwei
 * @date 2023/1/17
 */
public class Res<T> {
    private int code;

    private String msg;

    private T data;

    public Res(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Res<T> success(T data){
        return new Res<>(200,"success", data);
    }

    public static <T> Res<T> failure(ResEnum resEnum){
        return new Res<>(resEnum.getCode(),resEnum.getMsg(), null);
    }

}
