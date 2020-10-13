package com.xuegao.rocketmqproduct.nonannotation.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 该方法会在消息成功预写入RocketMQ后被执行
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        int value = transactionIndex.getAndIncrement();
        System.out.println("开始处理业务逻辑...");
        int status = value % 2;
        System.out.println("status = " + status);
        localTrans.put(msg.getTransactionId(), status);
        switch (status) {
            case 0:
                //LocalTransactionState.UNKNOW表示未知的事件，需要RocketMQ进一步服务业务进行确认该交易的处理
                //结果，确认消息被调用的方法为下方的checkLocalTransaction。
                //注：RocketMQ与业务确认消息的执行状态的功能已经被移除了，在早期3.0.8的版本中有该功能，因而如果
                //返回的状态为UNKNOW，则该消息不会被提交
                System.out.println("LocalTransactionState.UNKNOW = " + LocalTransactionState.UNKNOW.toString());
                return LocalTransactionState.UNKNOW;
            case 1:
                System.out.println("LocalTransactionState.COMMIT_MESSAGE = " + LocalTransactionState.COMMIT_MESSAGE.toString());
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                System.out.println("LocalTransactionState.ROLLBACK_MESSAGE = " + LocalTransactionState.ROLLBACK_MESSAGE.toString());
                return LocalTransactionState.ROLLBACK_MESSAGE;
            default:
                System.out.println("LocalTransactionState.COMMIT_MESSAGE = " + LocalTransactionState.COMMIT_MESSAGE.toString());
                return LocalTransactionState.COMMIT_MESSAGE;
        }
    }

    /**
     * 该方法用于RocketMQ与业务确认未提交事务的消息的状态，不过该方法已经的实现在RocketMQ中已经
     * 被删除了，因而其功能也就没有意义了。
     * 不过如果使用阿里云的企业的RocketMQ服务，该功能会起作用。
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        Integer status = localTrans.get(msg.getTransactionId());
        int mod = msg.getTransactionId().hashCode() % 2;
        if (null != status) {
            switch (mod) {
                case 0:
                    System.out.println("LocalTransactionState.ROLLBACK_MESSAGE = " + LocalTransactionState.ROLLBACK_MESSAGE);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                case 1:
                    System.out.println("LocalTransactionState.COMMIT_MESSAGE = " + LocalTransactionState.COMMIT_MESSAGE);
                    return LocalTransactionState.COMMIT_MESSAGE;
                default:
                    System.out.println("LocalTransactionState.COMMIT_MESSAGE = " + LocalTransactionState.COMMIT_MESSAGE);
                    return LocalTransactionState.COMMIT_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}