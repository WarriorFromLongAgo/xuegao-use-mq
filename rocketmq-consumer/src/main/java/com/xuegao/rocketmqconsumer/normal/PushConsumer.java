package com.xuegao.rocketmqconsumer.normal;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.normal
 * <br/> @ClassName：Consumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/25 11:04
 */
public class PushConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sync_queue");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // consumer.  setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");

        // Specify where to start in case the specified consumer group is a brand new one.
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // ConsumeMessageThread_1 Receive New Messages: Hello RocketMQ 0
        // ConsumeMessageThread_2 Receive New Messages: Hello RocketMQ 1
        // ConsumeMessageThread_3 Receive New Messages: Hello RocketMQ 3
        // ConsumeMessageThread_4 Receive New Messages: Hello RocketMQ 2
        // ConsumeMessageThread_5 Receive New Messages: Hello RocketMQ 4
        // ConsumeMessageThread_6 Receive New Messages: Hello RocketMQ 5
        // ConsumeMessageThread_8 Receive New Messages: Hello RocketMQ 7
        // ConsumeMessageThread_7 Receive New Messages: Hello RocketMQ 6
        // ConsumeMessageThread_9 Receive New Messages: Hello RocketMQ 8
        // ConsumeMessageThread_10 Receive New Messages: Hello RocketMQ 9

        // consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);


        /*
         * Subscribe one more more topics to consume.
         */
        consumer.subscribe("TopicTest", "*");

        /*
         *  Register callback to execute on arrival of messages fetched from brokers.
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                // System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msgs.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /*
         *  Launch the consumer instance.
         */
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}