package com.pay.service.impl;

import com.pay.domain.Order;
import com.pay.service.ConcurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ConcurrencyServiceImplTest {

    @Autowired
    private ConcurrencyService concurrencyService;


    @Test
    void testOrder() {

        for (int i = 0; i < 1000; i++) {
            final int num = i;
            new Thread(()->{
                Order order = new Order();
                order.setOrderId("orderId"+num);
                order.setName("name"+num);
                order.setOrderStatus("orderStatus"+num);
                order.setActualAmount(num);
                order.setRequireAmount(num);

                concurrencyService.order(order);
            }).start();

        }

    }
}



