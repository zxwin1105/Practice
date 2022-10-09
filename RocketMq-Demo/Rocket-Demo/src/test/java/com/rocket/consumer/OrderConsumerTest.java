package com.rocket.consumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhaixinwei
 * @date 2022/10/9
 */
class OrderConsumerTest {

    @Test
    void consumerOrder() throws MQClientException, InterruptedException {
        new OrderConsumer().consumerOrder();
        TimeUnit.MINUTES.sleep(100);
    }

    @Test
    void consumerPayment() throws MQClientException, InterruptedException {
        new OrderConsumer().consumerPayment();
        TimeUnit.MINUTES.sleep(100);
    }

    @Test
    void consumerShipping() throws MQClientException, InterruptedException {
        new OrderConsumer().consumerShipping();
        TimeUnit.MINUTES.sleep(100);
    }
}