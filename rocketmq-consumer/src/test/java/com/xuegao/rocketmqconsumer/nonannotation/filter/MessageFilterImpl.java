package com.xuegao.rocketmqconsumer.nonannotation.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * <br/> @PackageName：com.xuegao.rocketmqconsumer.nonannotation.filter
 * <br/> @ClassName：MessageFilterImpl
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/10/12 20:15
 */
public class MessageFilterImpl implements MessageFilter {
    @Override
    public boolean match(MessageExt msg, FilterContext context) {
        String a = msg.getProperty("a");
        if (StringUtils.isNotBlank(a)) {
            int integer = Integer.parseInt(a);
            return integer > 3;
        }
        return false;
    }
}