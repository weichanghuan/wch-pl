package com.hadsund.main.test;

import com.hadsund.main.autoregister.AutoListenerToRegister;
import com.hadsund.main.autoregister.AutoRegisterToPusher;
import com.hadsund.main.push.Pusher;
import com.hadsund.main.push.impl.DefPusher;
import com.hadsund.main.registry.Registry;
import com.hadsund.main.test.event.TransferEvent;
import com.hadsund.main.test.registry.TransactionRegistry;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 1:04 AM
 */
public class Test {

    public static void main(String[] args) throws Exception {
        // 容器上下文

        // 定义注册中心
        Registry registry = new TransactionRegistry();
        // 监听者自动注册到注册中心
        AutoListenerToRegister.annotationListener(registry,"com.hadsund.main.test.listener");

        // 定义event
        TransferEvent<String> event = new TransferEvent<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给XXX30亿");

        // 发送
        Pusher pusher = new DefPusher();
        // 注册中心与发送者绑定
        AutoRegisterToPusher.annotationRegister(pusher,"com.hadsund.main.test.registry");
        pusher.synSend(event);


    }
}
