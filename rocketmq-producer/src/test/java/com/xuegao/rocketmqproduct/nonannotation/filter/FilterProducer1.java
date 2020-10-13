package com.xuegao.rocketmqproduct.nonannotation.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.filter
 * <br/> @ClassName：FilterProducer1
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 20:01
 */
public class FilterProducer1 {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.start();

        for (int i = 0; i < 5; i++) {
            Message msg = new Message("TopicTestFilter",
                    "TagA",// tag
                    "OrderID001",//key
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // Set some properties.
            msg.putUserProperty("a", String.valueOf(i));
            msg.putUserProperty("b", String.valueOf(123));
            SendResult sendResult = producer.send(msg);
            System.out.println("=========================================");
            System.out.println(sendResult);
            System.out.println(msg);
            System.out.println("=========================================");
        }
        producer.shutdown();

    }
}