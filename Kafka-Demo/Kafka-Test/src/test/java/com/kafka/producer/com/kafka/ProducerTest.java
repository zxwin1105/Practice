package com.kafka.producer.com.kafka;

import com.kafka.producer.Producer;
import org.junit.Test;

/**
 * @author zhaixinwei
 * @date 2022/10/14
 */
public class ProducerTest {

    @Test
    public void sendRecordTest(){
        new Producer().sendRecord();
    }
}
