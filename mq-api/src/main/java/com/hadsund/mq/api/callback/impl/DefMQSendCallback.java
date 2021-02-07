package com.hadsund.mq.api.callback.impl;

import com.hadsund.mq.api.callback.MQSendCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/11/16 10:04 AM
 */
public class DefMQSendCallback implements MQSendCallback {

    private static final Logger logger = LoggerFactory.getLogger(DefMQSendCallback.class);

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onException(Throwable e, String message) {
        logger.error("send is fail,message:{},Throwable:{}", message, e.getMessage());
    }
}
