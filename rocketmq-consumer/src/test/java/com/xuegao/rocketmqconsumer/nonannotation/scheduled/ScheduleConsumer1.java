package com.xuegao.rocketmqconsumer.nonannotation.scheduled;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.scheduled
 * <br/> @ClassName：ScheduleConsumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 19:25
 */
public class ScheduleConsumer1 {
    public static void main(String[] args) throws Exception {
        // Instantiate message consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ExampleConsumer");
        consumer.setNamesrvAddr("192.168.42.131:9876");
        // Subscribe topics
        consumer.subscribe("TestTopic", "*");
        // Register message listener
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt message : messages) {
                    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                    // Print approximate delay time period
                    System.out.println("Receive message[msgId=" + message.getMsgId() + "] "
                            + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // Launch consumer
        consumer.start();
        System.out.println("consumer");
    }
}