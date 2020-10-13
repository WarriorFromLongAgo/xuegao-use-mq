package com.xuegao.rocketmqproduct.nonannotation.base;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation
 * <br/> @ClassName：SyncProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/11 11:29
 */
@SpringBootTest
public class SyncProducer1 {

    @Test
    public void send1() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 生产者的组名
        // producer.setProducerGroup("please_rename_unique_group_name");
        // please_rename_unique_group_name
        // 指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr("192.168.42.131:9876");
        // producer.setVipChannelEnabled(false);
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("TopicTest");
            message.setTags("TagA");
            message.setBody(("Hello RocketMQ 2020年10月12日17:07:41 " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);

            // TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }

}