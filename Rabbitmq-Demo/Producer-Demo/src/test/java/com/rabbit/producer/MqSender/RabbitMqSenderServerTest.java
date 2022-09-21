package com.rabbit.producer.MqSender;

import com.rabbit.producer.ProducerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaixinwei
 * @date 2022/9/19
 */
@SpringBootTest(classes = ProducerApplication.class)
public class RabbitMqSenderServerTest {

    @Autowired
    private RabbitMqSenderServer rabbitMqSenderServer;

    @Test
    public void testSend() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            rabbitMqSenderServer.sendBootQueue(i);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    @Test
    public void test_send_retry() {
        rabbitMqSenderServer.sendRetry();
    }

    @Test
    public void test_confirm() {
        rabbitMqSenderServer.sendAndConfirm();
    }
}
