package com.pay.service.impl;

import com.pay.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhaixinwei
 * @date: 2022/7/15
 * @description:
 */
@Service
public class CommonFactory {

    @Autowired
    private PaymentService paymentService;

    public PaymentService create(Order order){
        paymentService.setOrder(order);

        return paymentService;
    }
}
