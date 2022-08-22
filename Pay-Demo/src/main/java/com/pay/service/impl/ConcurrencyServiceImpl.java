package com.pay.service.impl;

import com.pay.domain.Order;
import com.pay.service.ConcurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhaixinwei
 * @date: 2022/7/15
 * @description:
 */
@Service
public class ConcurrencyServiceImpl implements ConcurrencyService {

    @Autowired
    private CommonFactory commonFactory;


    @Override
    public void order(Order order){
        PaymentService paymentService = commonFactory.create(order);
        paymentService.show();
    }
}
