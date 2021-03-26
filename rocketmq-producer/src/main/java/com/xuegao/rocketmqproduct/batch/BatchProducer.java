package com.xuegao.rocketmqproduct.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.batch
 * <br/> @ClassName：BatchProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/26 19:52
 */
public class BatchProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        String topic = "BatchTest";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));

        SendResult send = producer.send(messages);
        System.out.println(send.getSendStatus());

        producer.shutdown();
    }
}