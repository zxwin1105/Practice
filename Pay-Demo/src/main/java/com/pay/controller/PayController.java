package com.pay.controller;


import com.pay.Common.ResponseResult;
import com.pay.domain.Order;
import com.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: zhaixinwei
 * @date: 2022/6/18
 * @description: 支付接口
 */

@Slf4j
@RestController
@RequestMapping("/v1/pay")
public class PayController {

    @Autowired
    private IPayService payService;

    @PostMapping
    public ResponseResult<Map<String,String >> pay(String orderId){
        // 生成定单
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus("");
        order.setName("测试订单");
        order.setRequireAmount(1);
        order.setActualAmount(1);
        Map<String, String> nativeOrder = payService.createNative(order);
        // 统一微信下单生成二维码

        return ResponseResult.ok(nativeOrder);
    }

    @GetMapping("/payStatus/{orderId}")
    public ResponseResult<Map<String,String >> payStatus(@PathVariable("orderId") String orderId){
        Map<String, String> orderStatus = payService.payStatus(orderId);
        return ResponseResult.ok(orderStatus);
    }

    /**
     * 微信支付成功回调接口
     */
    @PostMapping("/notifyUrl/callback")
    public String callback(HttpServletRequest request){
        return payService.callback(request);
    }


}
