package com.kafka.producer;

import com.kafka.producer.Producer;
import org.junit.Test;

/**
 * @author zhaixinwei
 * @date 2022/10/14
 */
public class ProducerTest {

    @Test
    public void sendRecordTest() throws InterruptedException {
        new Producer().sendRecord();
    }
}
