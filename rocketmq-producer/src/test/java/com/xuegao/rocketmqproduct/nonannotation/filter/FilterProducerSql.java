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
public class FilterProducerSql {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("filter_producer_sql");
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.start();

        for (int i = 0; i < 4; i++) {
            Message message = new Message();
            message.setTopic("TopicTestFilterSql7");
            message.setTags("TagA");
            message.setBody(("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            message.putUserProperty("a", String.valueOf(i));

            // Set some properties.
            SendResult sendResult = producer.send(message);
            System.out.println("=========================================");
            System.out.println(sendResult);
            System.out.println(message);
            System.out.println("=========================================");

            // 加了延迟一秒，就都好了，不存在过滤消息的问题，还是会存在
            TimeUnit.SECONDS.sleep(2);
        }
        producer.shutdown();
    }
}