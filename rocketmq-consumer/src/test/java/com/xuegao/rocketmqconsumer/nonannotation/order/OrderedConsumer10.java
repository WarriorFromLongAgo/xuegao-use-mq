package com.xuegao.rocketmqconsumer.nonannotation.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.order
 * <br/> @ClassName：OrderedConsumer1
 * <br/> @Description：订阅模式 RocketMQ使用FIFO顺序提供有序消息。
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 18:10
 */
public class OrderedConsumer10 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
        consumer.setNamesrvAddr("192.168.42.131:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("TopicTestjjj", "TagA");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            AtomicLong consumeTimes = new AtomicLong(0);
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}