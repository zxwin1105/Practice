package com.rocket.consumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2022/10/9
 */
class PushConsumerTest {

    private final PushConsumer pushConsumer = new PushConsumer();

    @Test
    void consumeNormal() throws MQClientException, InterruptedException {
        pushConsumer.consumeNormal();
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    void consumeDelay() throws MQClientException, InterruptedException {
        pushConsumer.consumerDelayMessage();
        TimeUnit.SECONDS.sleep(100);

    }

    @Test
    void consumeBatch() throws MQClientException, InterruptedException {
        pushConsumer.consumerBatchMessage();
        TimeUnit.SECONDS.sleep(100);

    }
}