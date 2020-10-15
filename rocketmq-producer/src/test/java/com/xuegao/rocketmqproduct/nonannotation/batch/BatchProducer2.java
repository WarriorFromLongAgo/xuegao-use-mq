package com.xuegao.rocketmqproduct.nonannotation.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.batch
 * <br/> @ClassName：BatchProducer1
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 19:55
 */
public class BatchProducer2 {
    // 批量发送消息可提高传递小消息的性能
    // 此外，一批消息的总大小不得超过1MiB。
    // 如果您一次只发送不超过1MiB的消息，则可以轻松使用批处理

    // 消息必须有相同的 topic，相同的 waitStoreMsgOK，并且不是延迟的

    // http://rocketmq.apache.org/docs/batch-example/
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup("batchProducer");
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.start();

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("batchTopic");
            message.setTags("TagA");
            message.setBody(("Batch" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            messageList.add(message);
        }

        ListSplitter listSplitter = new ListSplitter(messageList);
        while (listSplitter.hasNext()){
            List<Message> next = listSplitter.next();
            SendResult send = producer.send(next);
            System.out.println("SendResult = " + send);
        }

        TimeUnit.SECONDS.sleep(1);

        producer.shutdown();
    }
}