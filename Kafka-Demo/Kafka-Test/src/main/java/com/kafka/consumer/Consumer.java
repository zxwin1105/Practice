package com.kafka.consumer;

import com.kafka.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * kafka消费者
 *
 * @author zhaixinwei
 * @date 2022/10/17
 */
@Slf4j()
public class Consumer {

    private final static String CONSUMER_GROUP_NAME = "test-group";

    public void consume() {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.11:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_NAME);
        // 是否自动提交offset，默认就是true
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 自动提交offset的间隔时间
//        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10");

        /*
          当消费主题的是一个新的消费组，或者指定offset的消费方式，offset不存在，那么应该如何消费
          latest(默认) ：只消费自己启动之后发送到主题的消息
          earliest：第一次从头开始消费，以后按照消费offset记录继续消费，这个需要区别于consumer.seekToBeginning(每次都从头开始消费)
          properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        */

        /*
           consumer给broker发送心跳的间隔时间，broker接收到心跳如果此时有rebalance发生会通过心跳响应将
           rebalance方案下发给consumer，这个时间可以稍微短一点
        */
//        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        /*
           服务端broker多久感知不到一个consumer心跳就认为他故障了，会将其踢出消费组，
           对应的Partition也会被重新分配给其他consumer，默认是10秒
        */
//        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);
        // 一次poll最大拉取消息的条数，如果消费者处理速度很快，可以设置大点，如果处理速度一般，可以设置小点
//        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        /*
         如果两次poll操作间隔超过了这个时间，broker就会认为这个consumer处理能力太弱，
         会将其踢出消费组，将分区分配给别的consumer消费
        */
//        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 消费者订阅topic
        consumer.subscribe(Collections.singletonList(Constant.TOPIC_TEST));
        // 消费指定分区
//        consumer.assign(Collections.singletonList(new TopicPartition(Constant.TOPIC_TEST, 0)));
        // 回溯消费，从头开始消费
//        consumer.assign(Collections.singletonList(new TopicPartition(Constant.TOPIC_TEST,0)));
//        consumer.seekToBeginning(Collections.singletonList(new TopicPartition(Constant.TOPIC_TEST,0)));

        // 指定offset消费
//        consumer.assign(Collections.singletonList(new TopicPartition(Constant.TOPIC_TEST,0)));
//        consumer.seek(new TopicPartition(Constant.TOPIC_TEST,0),10);
        
        // 指定时间点消费

        while (true) {
            // 去broker拉取记录，拉取时间1s
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info("消费消息：{}", record);
            }
            if (records.count() > 0) {
                consumer.commitAsync();
            }
        }

    }
}
