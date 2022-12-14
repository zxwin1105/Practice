package com.rabbit.producer.basicQuque;

import com.rabbit.producer.basicQueue.BasicQueueProducer;
import com.rabbit.producer.direct.DirectExchangeProducer;
import com.rabbit.producer.fanout.FanoutExchangeProducer;
import com.rabbit.producer.workQueue.WorkQueueProducer;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaixinwei
 * @date 2022/9/15
 */
public class BasicQueueProducerTest {

    @Test
    public void test_send_basic() throws IOException, TimeoutException {
        BasicQueueProducer basicQueueProducer = new BasicQueueProducer();
        basicQueueProducer.sendBasic();
    }

    @Test
    public void test_send_work() throws IOException, TimeoutException {
        WorkQueueProducer workQueueProducer = new WorkQueueProducer();
        workQueueProducer.sendWork();
    }

    @Test
    public void test_fanout_exchange() throws IOException, TimeoutException {
        FanoutExchangeProducer fanoutExchangeProducer = new FanoutExchangeProducer();
        fanoutExchangeProducer.sendFanout();
    }

    @Test
    public void test_direct_exchange() throws IOException, TimeoutException {
        new DirectExchangeProducer().sendDirect();
    }
}
