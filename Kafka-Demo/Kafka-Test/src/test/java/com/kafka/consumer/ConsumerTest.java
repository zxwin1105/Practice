package com.kafka.consumer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhaixinwei
 * @date 2022/10/17
 */
public class ConsumerTest {

    @Test
    public void consume() {
        new Consumer().consume();
    }
}