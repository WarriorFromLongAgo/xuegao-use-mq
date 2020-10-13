package com.xuegao.rocketmqproduct.nonannotation.scheduled;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.scheduled
 * <br/> @ClassName：ScheduleProducer1
 * <br/> @Description：延迟消息 您应该看到消息消耗比存储时间晚10秒。延迟的消息与普通的消息的不同之处在于，它们要等到指定的时间之后才能传递。
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 19:25
 */
public class ScheduleProducer1 {
    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        producer.setNamesrvAddr("192.168.42.131:9876");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 10;
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            System.out.println(message);

            // 不需要group相同，只需要topic相同就可以了
            // 时间单位支持：s、m、h、d，分别表示秒、分、时、天；
            // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h

            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(4);

            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}