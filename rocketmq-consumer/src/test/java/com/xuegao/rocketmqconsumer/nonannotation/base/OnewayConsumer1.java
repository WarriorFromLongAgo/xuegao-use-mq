package com.xuegao.rocketmqconsumer.nonannotation.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.base
 * <br/> @ClassName：OfficialConsumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 16:56
 */
public class OnewayConsumer1 {
    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // Specify name server addresses.
        consumer.setNamesrvAddr("192.168.42.131:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("======================================");
                    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
                    System.out.println(Arrays.toString(msg.getBody()));
                    System.out.println(msg.getBodyCRC());
                    System.out.println("======================================");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}