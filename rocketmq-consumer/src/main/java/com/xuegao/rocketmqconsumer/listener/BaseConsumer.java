package com.xuegao.rocketmqconsumer.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.listener
 * <br/> @ClassName：BaseConsumer
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/18 18:37
 */

// ConsumeMode.CONCURRENTLY
// ConsumeMode.ORDERLY 负载均衡
@RocketMQMessageListener(topic = "", consumerGroup = "${rocketmq.consumer.group}", consumeMode = ConsumeMode.CONCURRENTLY)
// @RocketMQMessageListener(topic = "", consumerGroup = "${rocketmq.consumer.group}", consumeMode = ConsumeMode.ORDERLY)
@Component
public class BaseConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("BaseConsumer onMessage = " + message);
    }
}