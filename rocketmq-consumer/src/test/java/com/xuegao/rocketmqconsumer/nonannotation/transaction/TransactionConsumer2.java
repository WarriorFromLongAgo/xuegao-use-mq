package com.xuegao.rocketmqconsumer.nonannotation.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.transaction
 * <br/> @ClassName：TransactionConsumer1
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/13 15:15
 */
public class TransactionConsumer2 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_producer");
        consumer.setNamesrvAddr("192.168.42.131:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTransaction", "*");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            private Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                try {
                    // 设置自动提交
                    context.setAutoCommit(true);
                    for (MessageExt msg : msgs) {
                        // if (msg.getTags().equalsIgnoreCase("TagB")) {
                        //     throw new RuntimeException("TagB 报错");
                        // }
                        System.out.println("获取到消息开始消费：" + msg + " , content : " + new String(msg.getBody()));
                    }

                    // 模拟业务处理
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (Exception e) {
                    e.printStackTrace();
                    // 返回处理失败，该消息后续可以继续被消费
                    // return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    // 回滚
                    return ConsumeOrderlyStatus.ROLLBACK;
                }
                //返回处理成功，该消息就不会再次投递过来了
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("consumer start ! ");
    }
}