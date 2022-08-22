package com.pay.controller;

import com.pay.domain.Order;
import com.pay.service.ConcurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhaixinwei
 * @date: 2022/7/15
 * @description:
 */
@RestController
@RequestMapping("/concurrent")
public class ConcurrencyController {


    @Autowired
    private ConcurrencyService concurrencyService;

    @PostMapping("/order")
    public void concurrency(@RequestBody Order order){
        concurrencyService.order(order);
    }
}
