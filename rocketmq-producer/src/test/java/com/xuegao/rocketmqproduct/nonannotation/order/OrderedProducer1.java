package com.xuegao.rocketmqproduct.nonannotation.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.order
 * <br/> @ClassName：OrderedProducer1
 * <br/> @Description：订阅模式 RocketMQ使用FIFO顺序提供有序消息。
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 18:10
 */
public class OrderedProducer1 {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("example_group_name");
        //Launch the instance.
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 6; i++) {
            int orderId = i % 6;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTestjjj", tags[i % tags.length], "KEY" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            System.out.println(msg);
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    System.out.println(mqs);
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    MessageQueue messageQueue = mqs.get(index);
                    System.out.println(index);
                    System.out.println(messageQueue);
                    return messageQueue;
                }
            }, orderId);

            System.out.printf("%s%n", sendResult);
        }
        //server shutdown
        producer.shutdown();
    }
}