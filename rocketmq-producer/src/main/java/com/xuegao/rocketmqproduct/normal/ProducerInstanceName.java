package com.xuegao.rocketmqproduct.normal;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.normal
 * <br/> @ClassName：Producer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/25 11:04
 */
public class ProducerInstanceName {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        // producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
        producer.setInstanceName("ProducerInstanceName");
        producer.start();
        for (int i = 0; i < 10; i++) {
            try {

                /*
                 * Create a message instance, specifying topic, tag and message body.
                 */
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(StandardCharsets.UTF_8) /* Message body */
                );

                /*
                 * Call send message to deliver message to one of brokers.
                 */
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
         * Shut down once the producer instance is not longer in use.
         */
        producer.shutdown();


    }
}