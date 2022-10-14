package com.kafka;

/**
 * kafka测试 使用的常量
 * @author zhaixinwei
 * @date 2022/10/14
 */
public class Constant {

    /** properties key */
    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";

    public static final String ACK = "acks";

    public static final String RETRIES = "retries";

    public static final String BATCH_SIZE = "batch.size";

    public static final String LINGER_MS = "linger.ms";

    public static final String BUFFER_MEMORY = "buffer.memory";

    public static final String KEY_SERIALIZER = "key.serializer";

    public static final String VALUE_SERIALIZER = "value.serializer";

    /** properties value */

    public static final String BOOTSTRAP_SERVERS_VALUE = "192.168.56.11:9092";

    public static final String ACK_VALUE = "all";

    public static final int RETRIES_VALUE = 0;

    public static final int BATCH_SIZE_VALUE = 16389;

    public static final int LINGER_MS_VALUE = 1;

    public static final int BUFFER_MEMORY_VALUE = 33554432;

    public static final String KEY_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";

    public static final String VALUE_SERIALIZER_VALUE = "org.apache.kafka.common.serialization.StringSerializer";


    /** topic name */
    public static final String TOPIC_TEST = "test-topic";


}

