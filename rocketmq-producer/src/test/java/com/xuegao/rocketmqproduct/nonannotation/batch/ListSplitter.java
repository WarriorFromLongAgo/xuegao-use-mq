package com.xuegao.rocketmqproduct.nonannotation.batch;

import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <br/> @PackageName：com.xuegao.rocketmqproduct.nonannotation.batch
 * <br/> @ClassName：ListSplitter
 * <br/> @Description：切割
 * <br/> @author：xuegao
 * <br/> @date：2020/10/14 20:48
 */
public class ListSplitter implements Iterator<List<Message>> {

    private final int SIZE_LIMIT = 1024 * 1024 * 4;
    private final List<Message> messageList;
    private int currIndex;

    public ListSplitter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public boolean hasNext() {
        return currIndex < messageList.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currIndex;
        int totalSize = 0;

        for (; nextIndex < messageList.size(); nextIndex++) {
            Message message = messageList.get(nextIndex);
            int messageSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> messageProperties = message.getProperties();
            for (Map.Entry<String, String> entry : messageProperties.entrySet()) {
                messageSize += entry.getKey().length() + entry.getValue().length();
            }
            // 增加日志的开销20个字节
            messageSize += 20;
            if (messageSize > SIZE_LIMIT) {
                // 单个消息超过了最大的限制
                // 忽略，否则会阻塞分裂的进程
                if (nextIndex - currIndex == 0) {
                    // 例如下一个子列表没有元素，则添加这个子列表，然后退出循环，否则只是退出循环
                    nextIndex++;
                }
                break;
            }
            if (messageSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += messageSize;
            }
        }
        List<Message> subList = this.messageList.subList(currIndex, nextIndex);
        currIndex = nextIndex;

        return subList;
    }
}