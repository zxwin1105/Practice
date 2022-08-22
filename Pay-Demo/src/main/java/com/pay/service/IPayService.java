package com.pay.service;

import com.pay.domain.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: zhaixinwei
 * @date: 2022/6/22
 * @description:
 */
public interface IPayService {

    /** 创建NATIVE支付 */
    Map<String,String> createNative(Order order);

    /** 通过商户ID查询订单支付状态 */
    Map<String, String> payStatus(String orderId);

    String callback(HttpServletRequest request);
}
