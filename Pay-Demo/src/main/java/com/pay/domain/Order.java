package com.pay.domain;

/**
 * @author: zhaixinwei
 * @date: 2022/6/22
 * @description:
 */

import lombok.Data;

@Data
public class Order {


    private String orderId;
    private String name;
    private String orderStatus;
    private Integer actualAmount;
    private Integer requireAmount;
}
