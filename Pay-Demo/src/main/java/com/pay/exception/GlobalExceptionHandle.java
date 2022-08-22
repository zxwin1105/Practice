package com.pay.exception;

import com.pay.Common.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: zhaixinwei
 * @date: 2022/6/19
 * @description: 全局异常处理
 */
@RestControllerAdvice("com.pay")
public class GlobalExceptionHandle {

    @ExceptionHandler(value = ServiceException.class)
    public ResponseResult<String> serviceException(ServiceException e){
        return ResponseResult.error(e.getCode(),e.getMsg());
    }
}
