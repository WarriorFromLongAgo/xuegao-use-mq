package com.xuegao.rocketmqconsumer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer
 * <br/> @ClassName：RocketMQTest
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/18 18:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RocketmqConsumerApplication.class})
public class RocketMQTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void sendTest() {
       // rocketMQTemplate.send( );
    }

    @Test
    public void convertAndSendTest() {
        // 主题
        // 消息内容
        rocketMQTemplate.convertAndSend("convertAndSend", "springboot-producer-group convertAndSend");
    }

    @Test
    public void sendAndReceiveTest() {

    }

    @Test
    public void syncSendTest() {

    }

    @Test
    public void asyncSendTest() {

    }

    @Test
    public void syncSendOrderlyTest() {

    }

    @Test
    public void asyncSendOrderlyTest() {

    }

    @Test
    public void sendOneWayTest() {

    }

}