package com.xuegao.rocketmqconsumer.nonannotation.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.transaction
 * <br/> @ClassName：TransactionBiliConsumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/18 17:04
 */
public class TransactionBiliConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_consumer");
        consumer.setNamesrvAddr("192.168.42.131:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("transactionTopic", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : messageExtList) {
                    System.out.println("===================================");
                    System.out.println(Thread.currentThread().getName() + "===" + new String(messageExt.getBody()));
                    System.out.println("===================================");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("consumer 启动");
    }
}