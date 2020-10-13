package com.xuegao.rocketmqproduct.nonannotation.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.base
 * <br/> @ClassName：OnewayProducer1
 * <br/> @Description：以单向模式发送消息 单向传输用于要求中等可靠性的情况，例如日志收集。
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 17:41
 */
public class OnewayProducer1 {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.42.131:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 3; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
            System.out.println("sendOneway = " + msg.toString());
        }
        //Wait for sending to complete
        producer.shutdown();
    }
}