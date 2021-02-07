package com.hadsund.mq.api;

import com.hadsund.mq.api.callback.MQSendCallback;

import java.util.Set;

public interface Producer {

    /**
     * 发布消息
     * @param topic
     * @param message
     * @Param sendCallback
     * @throws Exception
     */
    void publishMessageSyn(String topic, String message, String tag, MQSendCallback MQSendCallback) throws Exception;

    /**
     * 获取主题集合
     * @return
     * @throws Exception
     */
    Set<String> getTopicList() throws Exception;


}
