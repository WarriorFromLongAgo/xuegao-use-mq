package com.xuegao.rocketmqconsumer.nonannotation.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.base
 * <br/> @ClassName：consumer2
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 17:18
 */
public class SyncConsumer2 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        // please_rename_unique_group_name
        consumer.setConsumerGroup("xuegao_base");
        // 指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr("192.168.42.131:9876");
        consumer.setVipChannelEnabled(false);
        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");
        // 程序第一次启动从消息队列头获取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //可以修改每次消费消息的数量，默认设置是每次消费一条
        consumer.setConsumeMessageBatchMaxSize(1);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                // 会把不同的消息分别放置到不同的队列中
                // System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for (MessageExt msg : msgs) {
                    System.out.println("======================================");
                    System.out.println(Thread.currentThread().getName() + "==" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
                    System.out.println(Arrays.toString(msg.getBody()));
                    System.out.println(msg.getBodyCRC());
                    System.out.println("======================================");
                }
                // 消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}