package com.xuegao.rocketmqproduct.async;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.sync
 * <br/> @ClassName：SyncProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/25 16:14
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("async_queue");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("ASyncProducer " + i).getBytes(StandardCharsets.UTF_8)
            );
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    String nowDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    System.out.println("--------------" + nowDateTime);
                    System.err.println("发送成功 = " + sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    String nowDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    System.out.println("--------------" + nowDateTime);
                    System.err.println("发送失败 = " + e);
                }
            });
            // 如果没有这样，那么消息发送完了就直接关闭了，甚至会报错连接不上127.0.0.1:9876
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}