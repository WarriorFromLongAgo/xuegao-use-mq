package com.xuegao.rocketmqproduct.nonannotation.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.base
 * <br/> @ClassName：AsyncProducer1
 * <br/> @Description：异步发送消息 异步传输通常用于对时间敏感的业务场景中
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 17:31
 */
public class AsyncProducer1 {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("xuegao_base");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.42.131:9876");
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                Message msg = new Message("Jodie_topic_1023",
                        "TagA",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, sendResult);
                    }

                    @Override
                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}