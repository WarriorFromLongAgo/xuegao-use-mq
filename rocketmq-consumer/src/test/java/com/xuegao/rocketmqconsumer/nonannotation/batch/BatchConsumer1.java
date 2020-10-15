package com.xuegao.rocketmqconsumer.nonannotation.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.batch
 * <br/> @ClassName：BatchConsumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 19:53
 */
public class BatchConsumer1 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup("batchConsumer");
        consumer.setNamesrvAddr("192.168.42.131:9876");

        consumer.subscribe("batchTopic", "TagA");
        // 广播模式
        // consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : messageExtList) {
                    System.out.println(Thread.currentThread().getName() + ", " + new String(messageExt.getBody()));
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.println("consumer");
    }
}