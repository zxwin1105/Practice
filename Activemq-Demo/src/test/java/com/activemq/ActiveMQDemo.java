package com.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;

/**
 * @author: zhaixinwei
 * @date: 2022/6/20
 * @description: 学习active的使用方法
 */
public class ActiveMQDemo {

    @Test
    public void process() throws JMSException {
        // 1.创建连接工厂ConnectiveFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin","admin","tcp://localhost:61616"
        );
        /*
            这里存在安全隐患，可以在activemq.xml中指定的用户和密码才能访问mq。
            除了tcp协议外，activemq还支持多种协议
         */
        
        // 2.创建Connection
        Connection connection = connectionFactory.createConnection();
        /*
            Connection是应用程序和消息服务器之间的通信链路。
         */

        // 3.创建Session(上下文环境对象)。这里有一些配置参数，如是否启用事务，签收模式等。
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        /*
            Session用于发送和接收消息，且是单线程的，支持事务的。如果Session开启了事务支持，Session中保存的
            信息，要么commit到MQ，要么回滚。Session可以用来创建MessageProducer/MessageConsumer
         */

        // 4.创建Destination
        Queue testQueue = session.createQueue("testQueue");
//        Topic testTopic = session.createTopic("testTopic");

        /*
            Queue,Topic extends Destination
            Destination包括Queue和Topic。PTP模式下就是Queue；pub/sub模式下是Topic
         */

        // 5.创建MessageProducer/MessageConsumer
        MessageProducer producer = session.createProducer(testQueue);
//        MessageConsumer consumer = session.createConsumer(testQueue);
        /*
            创建生产者/消费者
         */

        // 6.设置持久化方式
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // 7.定义消息发送消息
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("hello ActiveMQ");
        producer.send(textMessage);
    }



    public void consumer(){

    }
}
