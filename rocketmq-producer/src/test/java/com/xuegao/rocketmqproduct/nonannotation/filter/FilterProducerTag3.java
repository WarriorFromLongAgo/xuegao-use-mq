package com.xuegao.rocketmqproduct.nonannotation.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.filter
 * <br/> @ClassName：FilterProducer1
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 20:01
 */
public class FilterProducerTag3 {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.start();

        String[] stringArr = {"TagA", "TagB", "TagC"};
        for (int i = 0; i < 6; i++) {
            Message message = new Message();
            message.setTopic("TopicTestFilter");
            message.setTags(stringArr[i % stringArr.length]);
            // message.setTags("TagA");
            message.setKeys("KEY" + i);
            message.setBody(("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // Set some properties.
            SendResult sendResult = producer.send(message);
            System.out.println("=========================================");
            System.out.println(sendResult);
            System.out.println(message);
            System.out.println("=========================================");

            // 加了延迟一秒，就都好了，不存在过滤消息的问题
            TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}