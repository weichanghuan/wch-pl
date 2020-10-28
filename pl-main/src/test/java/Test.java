import org.pl.main.event.Event;
import org.pl.main.listener.Listener;
import org.pl.main.push.Pusher;
import org.pl.main.push.impl.DefPusher;
import org.pl.main.registry.Registry;

import java.util.UUID;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 1:04 AM
 */
public class Test {

    public static void main(String[] args) {
        // 容器上下文

        // 定义转账的监听者
        Listener listener = new Listener() {
            @Override
            public String matchEventQueue() {
                return "transfer";
            }

            @Override
            public void listen(Event event) {
                TransferEvent<String> transferEvent = null;
                if (event instanceof TransferEvent) {
                    transferEvent = (TransferEvent<String>) event;
                }
                String content = transferEvent.getContent();
                System.out.println(content);
            }
        };

        // 定义注册中心
        Registry registry = new Registry() {
            @Override
            public String getListenerId() {
                return UUID.randomUUID().toString();
            }
        };
        registry.addListener(listener);


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
