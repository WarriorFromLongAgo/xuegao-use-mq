package com.xuegao.rocketmqproduct.delay;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.delay
 * <br/> @ClassName：DealayProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/26 19:35
 */
public class DelayProducer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("sync_queue");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            try {
                String nowDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                Message msg = new Message("TopicTest",
                        "TagA",
                        ("DelayProducer " + i + " " + nowDateTime).getBytes(StandardCharsets.UTF_8)
                );
                msg.setDelayTimeLevel(3);
                SendResult sendResult = producer.send(msg);

                SendStatus status = sendResult.getSendStatus();
                System.err.println(status);
                System.err.println("消息发出: " + sendResult);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}