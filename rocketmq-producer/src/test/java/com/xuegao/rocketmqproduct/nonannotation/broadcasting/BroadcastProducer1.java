package com.xuegao.rocketmqproduct.nonannotation.broadcasting;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.broadcasting
 * <br/> @ClassName：BroadcastProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 19:18
 */
public class BroadcastProducer1 {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.start();

        // 广播模式，必须是 topic 和 tags 都相同
        // group 可以不相同
        // 就可以收到消息
        // 广播正在向主题的所有订户发送一条消息。如果您希望所有订阅者都收到有关某个主题的消息，那么广播是一个不错的选择。

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}