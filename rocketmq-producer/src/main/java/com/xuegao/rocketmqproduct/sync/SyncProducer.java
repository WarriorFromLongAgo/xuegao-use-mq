package com.xuegao.rocketmqproduct.sync;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.sync
 * <br/> @ClassName：SyncProducer
 * <br/> @Description：默认发送的都是同步的消息，必须要等broker处理完成
 * <br/> @author：xuegao
 * <br/> @date：2021/03/25 16:14
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("sync_queue");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message("TopicTest",
                        "TagA",
                        ("SyncProducer " + i).getBytes(StandardCharsets.UTF_8)
                );
                SendResult sendResult = producer.send(msg, 3000);

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