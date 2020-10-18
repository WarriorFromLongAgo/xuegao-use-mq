package com.xuegao.rocketmqproduct.nonannotation.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.transaction
 * <br/> @ClassName：TransactionBiliProducer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/18 17:04
 */
public class TransactionBiliProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer");
        producer.setNamesrvAddr("192.168.42.131:9876");
        producer.setTransactionListener(new TransactionListener() {
            // 该方法中，执行本地的事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
                // 本地事务，如果是XX，就执行提交事务操作
                if (StringUtils.equalsIgnoreCase("TagA", message.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equalsIgnoreCase("TagB", message.getTags())) {
                    // 回滚
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equalsIgnoreCase("TagC", message.getTags())) {
                    return LocalTransactionState.UNKNOW;
                } else {
                    // 不做处理，就要回查了
                    return LocalTransactionState.UNKNOW;
                }
            }

            // mq 回查生产者消息事务状态
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                System.out.println("Tags = " + messageExt.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.start();

        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setTopic("transactionTopic");
            message.setTags(tags[i]);
            message.setBody(("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            // arg = 将事务应用到一个消息，或者是所有的消息也就是producer
            // null 就是所有
            SendResult sendResult = producer.sendMessageInTransaction(message, null);
            System.out.println("=====================================");
            System.out.println(message);
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("=====================================");
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        }

        // 不停止事务的生产者

    }
}