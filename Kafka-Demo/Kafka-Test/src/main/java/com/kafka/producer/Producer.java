package com.kafka.producer;

import com.kafka.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * kafka生产者生成记录
 * kafka生产者是线程安全的，在线程之间共享单个实例
 *
 * @author zhaixinwei
 * @date 2022/10/14
 */
@Slf4j
public class Producer {

    /**
     * 向kafka中发送 记录
     */
    public void sendRecord() throws InterruptedException {
        // 生产者配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.11:9092");
        /*
          发出消息持久化机制参数
        （1）acks=0： 表示producer不需要等待任何broker确认收到消息的回复，就可以继续发送下一条消息。性能最高，但是最容易丢消息。
        （2）acks=1： 至少要等待leader已经成功将数据写入本地log，但是不需要等待所有follower是否成功写入。就可以继续发送下一
        条消息。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
        （3）acks=‐1或all： 需要等待 min.insync.replicas(默认为1，推荐配置大于等于2) 这个参数配置的副本个数都成功写入日志，这种策
        略会保证
         只要有一个备份存活就不会丢失数据。这是最强的数据保证。一般除非是金融级别，或跟钱打交道的场景才会使用这种配置。
        */
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        /*
        发送失败会重试，默认重试间隔100ms，重试能保证消息发送的可靠性，但是也可能造成消息重复发送，比如网络抖动，所以需要在
        接收者那边做好消息接收的幂等性处理
         */
        properties.put(ProducerConfig.RETRIES_CONFIG, 100);
        //kafka本地线程会从缓冲区取数据，批量发送到broker，
        // 设置批量发送消息的大小，默认值是16384，即16kb，就是说一个batch满了16kb就发送出去
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        /*
        默认值是0，意思就是消息必须立即被发送，但这样会影响性能
        一般设置10毫秒左右，就是说这个消息发送完后会进入本地的一个batch，如果10毫秒内，这个batch满了16kb就会随batch一起被发送出去
        如果10毫秒内，batch没满，那么也必须把消息发送出去，不能让消息的发送延迟时间太长
         */
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        // 设置发送消息的本地缓冲区，如果设置了该缓冲区，消息会先发送到本地缓冲区，可以提高消息发送性能，默认值是33554432，即32MB
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 创建一个kafka生产者
        org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(properties);


        CountDownLatch countDownLatch = new CountDownLatch(900000000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 900000000; i++) {
            // 构造消息记录
            /*
             1.指定消息发送到的分区
             ProducerRecord<String, String> record = new ProducerRecord<>(Constant.TOPIC_TEST, 0,Integer.toString(i), Integer.toString(i));
             */
            // 2.不指定消息发送到的分区，具体发送的分区计算公式：hash(key)%partitionNum
            ProducerRecord<String, String> record = new ProducerRecord<>(Constant.TOPIC_TEST, Integer.toString(i), Integer.toString(i));

            // 发布消息并接收返回值
            /*
            1. 同步阻塞接收，消息的发送是异步的，但是Future的get方法是同步阻塞的
               Future<RecordMetadata> send = producer.send(record);
             */

            // 2. 异步发送接口
            producer.send(record, (recordMetadata, e) -> {
                if (e != null) {
                    log.error("发送消息失败{}", e.getMessage());
                }else {
                    log.info("发送消息成功{}",record);
                }
                countDownLatch.countDown();
            });
        }
        long end = System.currentTimeMillis();
        countDownLatch.await();
        log.info("time:{}",end - start);
        producer.close();
    }
}
