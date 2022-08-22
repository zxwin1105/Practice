package com.pay.service.impl;

import com.pay.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhaixinwei
 * @date: 2022/7/15
 * @description:
 */
@Slf4j
@Service
public class PaymentService {
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }
    public void show(){

            Integer actualAmount = order.getActualAmount();
            log.error("当前order:{},info:{}",actualAmount,order.toString());
//            try {
//                TimeUnit.MILLISECONDS.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            log.info("当前order:{},info:{}",actualAmount,order.toString());
    }
}




