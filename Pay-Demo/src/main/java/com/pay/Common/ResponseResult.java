package com.pay.Common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.internal.dynalink.beans.StaticClass;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhaixinwei
 * @date: 2022/6/19
 * @description: 全局响应
 */
@Data
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 42L;
    private int code;
    private T data;
    private String msg;

    public ResponseResult(int code,T data,String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ResponseResult<T> ok(){

        return new ResponseResult<>(200,null,null);
    }

    public static <T> ResponseResult<T> ok(T data){
        return new ResponseResult<T>(200,data,null);
    }

    public static <T> ResponseResult<T> error(String msg){
        return new ResponseResult<>(500,null,msg);
    }
    public static <T> ResponseResult<T> error(int code,String msg){
        return new ResponseResult<>(code,null,msg);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
