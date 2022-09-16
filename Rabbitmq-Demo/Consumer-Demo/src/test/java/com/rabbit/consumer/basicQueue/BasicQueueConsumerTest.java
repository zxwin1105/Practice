package com.rabbit.consumer.basicQueue;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaixinwei
 * @date 2022/9/15
 */
public class BasicQueueConsumerTest {

    @Test
    public void test_pull_basic() throws IOException, TimeoutException {
        BasicQueueConsumer basicQueueConsumer = new BasicQueueConsumer();
        basicQueueConsumer.pullBasic();
    }
}
