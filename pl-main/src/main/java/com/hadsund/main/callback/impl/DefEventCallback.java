package com.hadsund.main.callback.impl;

import com.alibaba.fastjson.JSONObject;
import com.hadsund.main.callback.EventCallback;
import com.hadsund.main.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/11/16 10:04 AM
 */

public class DefEventCallback implements EventCallback, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(DefEventCallback.class);

    @Override
    public void onSuccess(Event event) {

    }

    @Override
    public void onException(Throwable e, Event event) {
        String s = JSONObject.toJSONString(event);
        System.out.println("111:"+s);
        logger.error("send is fail,Event:"+s+",Throwable:"+e.getMessage());
    }
}
