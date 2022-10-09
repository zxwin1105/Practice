package com.rocket.message;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhaixinwei
 * @date 2022/10/9
 */
class NormalMessageTest {

    private final NormalMessage normalMessage = new NormalMessage();

    @Test
    void sendSyncMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        normalMessage.sendSyncMessage();
    }

    @Test
    void sendAsyncMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        normalMessage.sendAsyncMessage();
    }

    @Test
    void sendOnewayMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        normalMessage.sendOnewayMessage();
    }
}