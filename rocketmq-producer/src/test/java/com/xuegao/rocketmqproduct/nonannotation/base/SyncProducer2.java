package com.xuegao.rocketmqproduct.nonannotation.base;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.base
 * <br/> @ClassName：producer2
 * <br/> @Description：同步发送消息 在重要的通知消息，SMS通知，SMS营销系统等广泛的场景中使用可靠的同步传输
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 17:17
 */
public class SyncProducer2 {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer();
        // 生产者的组名
        producer.setProducerGroup("xuegao_base");
        // please_rename_unique_group_name
        // 指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.setVipChannelEnabled(false);
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