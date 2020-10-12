package com.xuegao.rocketmqconsumer.nonannotation.base;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation
 * <br/> @ClassName：SyncProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/11 11:29
 */
@SpringBootTest
public class consumer1 {

    @Test
    public void consumer1() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        // please_rename_unique_group_name
        consumer.setConsumerGroup("xuegaoConsumer");
        // 指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr("192.168.200.131:9876");
        consumer.setVipChannelEnabled(false);
        // 订阅PushTopic下Tag为push的消息,都订阅消息
        // consumer.subscribe("TopicTest", "push");
        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");
        // 程序第一次启动从消息队列头获取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //可以修改每次消费消息的数量，默认设置是每次消费一条
        consumer.setConsumeMessageBatchMaxSize(1);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                // System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 会把不同的消息分别放置到不同的队列中
                try {
                    for (Message msg : msgs) {
                        System.out.println("接收到了消息：" + new String(msg.getBody()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 稍后再试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
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