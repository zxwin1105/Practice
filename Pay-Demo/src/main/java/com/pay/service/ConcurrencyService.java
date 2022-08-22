package com.pay.service;

import com.pay.domain.Order;

/**
 * @author: zhaixinwei
 * @date: 2022/7/15
 * @description:
 */
public interface ConcurrencyService {

    void order(Order order);
}
