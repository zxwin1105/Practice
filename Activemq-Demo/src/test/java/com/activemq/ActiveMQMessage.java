package com.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.region.Destination;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.*;

/**
 * @author: zhaixinwei
 * @date: 2022/6/27
 * @description:
 */
public class ActiveMQMessage {

    private Connection connection;
    private Session session;


    @BeforeEach
    public void before(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "amdin", "tcp://localhost:61616");
        try {
            connection = activeMQConnectionFactory.createConnection();
            connection.setClientID("connection-1");
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void after(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void producer(){

        MessageProducer producer = null;
        try {
            Topic testTopic = session.createTopic("testTopic");
            producer = session.createProducer(testTopic);
            // 数据持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("first test mq");
            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void consumer(){
        MessageConsumer consumer = null;
        try {
            Topic testTopic = session.createTopic("testTopic");

            TopicSubscriber durableSubscriber = session.createDurableSubscriber(testTopic, "connection-1");
            durableSubscriber.setMessageListener((message)->{
                if(message instanceof TextMessage){
                    try {
                        System.out.println("消费了testTopic:" + ((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
