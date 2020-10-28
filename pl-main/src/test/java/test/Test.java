package test;

import org.pl.main.autoregister.AutoRegisterListener;
import org.pl.main.push.Pusher;
import org.pl.main.push.impl.DefPusher;
import org.pl.main.registry.Registry;

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
        // 自动注册
        AutoRegisterListener.annotationListener(registry,"test.listener");

        // 定义event
        TransferEvent<String> event = new TransferEvent<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给危常焕30亿");

        // 发送
        Pusher pusher = new DefPusher();
        pusher.addRegistry("付款注册中心", registry);
        pusher.synSend(event);


    }
}
