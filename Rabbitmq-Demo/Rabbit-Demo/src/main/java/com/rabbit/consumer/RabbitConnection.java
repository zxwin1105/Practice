package com.rabbit.consumer;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取rabbitmq连接
 * @author zhaixinwei
 * @date 2022/9/15
 */
public class RabbitConnection {

    private static final Connection CONNECTION;

    private static final String USERNAME = "rabbit";

    private static final String PASSWORD = "rabbit";

    private static final String RABBIT_HOST = "192.168.56.11";

    private static final int PORT = 5672;
    static {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBIT_HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        Connection connection = null;
        try {
            // 获取TCP长连接
            connection = connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        CONNECTION = connection;

    }

    public static Connection getRabbitConnection(){
        return CONNECTION;
    }

}
