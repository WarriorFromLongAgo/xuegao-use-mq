package com.xuegao.rocketmqconsumer.normal;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.normal
 * <br/> @ClassName：PullConsumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2021/03/25 14:10
 */
public class PullConsumer {
    public static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        // 1、创建DefaultLitePullConsumer对象
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("lite_pullConsumer");
        // 2、设置namesrv地址
        litePullConsumer.setNamesrvAddr("localhost:9876");
        // 3、订阅消费主题
        litePullConsumer.subscribe("TopicTest", "*");
        // 4、启动消费对象
        litePullConsumer.start();
        try {
            // 5、循环开始消费消息
            while (running) {
                List<MessageExt> messageExts = litePullConsumer.poll();
                if (messageExts.isEmpty()) {
                    continue;
                }
                for (MessageExt messageExt : messageExts) {
                    System.out.println(new String(messageExt.getBody()));
                }
            }
        } finally {
            litePullConsumer.shutdown();
        }
    }
}