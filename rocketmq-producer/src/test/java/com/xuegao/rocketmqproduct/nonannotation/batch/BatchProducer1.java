package com.xuegao.rocketmqproduct.nonannotation.batch;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.batch
 * <br/> @ClassName：BatchProducer1
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 19:55
 */
public class BatchProducer1 {
    public static void main(String[] args) {
        // 批量发送消息可提高传递小消息的性能
        // 此外，一批消息的总大小不得超过1MiB。
        // 如果您一次只发送不超过1MiB的消息，则可以轻松使用批处理

        // 消息必须有相同的 topic，相同的 waitStoreMsgOK，并且不是延迟的

        // http://rocketmq.apache.org/docs/batch-example/


    }
}