package com.rocket.message;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhaixinwei
 * @date 2022/10/10
 */
class DelayMessageTest {
    private DelayMessage delayMessage = new DelayMessage();
    @Test
    void sendDelayMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        delayMessage.sendDelayMessage();
    }
}