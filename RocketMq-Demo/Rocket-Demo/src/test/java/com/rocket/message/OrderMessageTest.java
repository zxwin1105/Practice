package com.rocket.message;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhaixinwei
 * @date 2022/10/9
 */
class OrderMessageTest {

    private final OrderMessage orderMessage = new OrderMessage();
    @Test
    void sendOrderMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        orderMessage.sendOrderMessage();
    }
}