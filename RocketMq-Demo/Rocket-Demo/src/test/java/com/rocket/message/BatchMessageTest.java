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
class BatchMessageTest {

    @Test
    void sendBatchMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        new BatchMessage().sendBatchMessage();
    }
}