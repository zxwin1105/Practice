package com.kafka.producer;

import com.kafka.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

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
    public void sendRecord() {
        // 生产者配置
        Properties properties = new Properties();
        properties.put(Constant.BOOTSTRAP_SERVERS, Constant.BOOTSTRAP_SERVERS_VALUE);
        properties.put(Constant.ACK, Constant.ACK_VALUE);
        properties.put(Constant.RETRIES, Constant.RETRIES_VALUE);
        properties.put(Constant.BATCH_SIZE, Constant.BATCH_SIZE_VALUE);
        properties.put(Constant.LINGER_MS, Constant.LINGER_MS_VALUE);
        properties.put(Constant.BUFFER_MEMORY, Constant.BUFFER_MEMORY_VALUE);
        properties.put(Constant.KEY_SERIALIZER, Constant.KEY_SERIALIZER_VALUE);
        properties.put(Constant.VALUE_SERIALIZER, Constant.VALUE_SERIALIZER_VALUE);
        // 创建一个kafka生产者
        org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(Constant.TOPIC_TEST,Integer.toString(i),Integer.toString(i));
            producer.send(record);
        }
        producer.close();
    }
}
