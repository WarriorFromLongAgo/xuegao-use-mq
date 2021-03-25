package com.xuegao.rocketmqproduct.sort;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.sort
 * <br/> @ClassName：SortProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/25 15:51
 */
public class SortProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
        producer.setNamesrvAddr("127.0.0.1:9876");

        producer.start();

        // String[] tags = new String[] { "TagA", "TagB", "TagC", "TagD",
        // "TagE" };

        for (int i = 1; i <= 5; i++) {

            Message msg = new Message("TopicOrderTest", "order_1", "KEY" + i, ("order_1 " + i).getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, 0);

            System.out.println(sendResult);
        }

        producer.shutdown();
    }
}