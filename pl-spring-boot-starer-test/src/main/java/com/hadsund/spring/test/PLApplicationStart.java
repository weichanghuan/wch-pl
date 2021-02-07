package com.hadsund.spring.test;

import com.hadsund.main.event.DelayEvent;
import com.hadsund.main.push.Pusher;
import com.hadsund.spring.test.dto.TestAA;
import com.hadsund.spring.test.event.TransferEvent2;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
@RestController
public class PLApplicationStart implements CommandLineRunner {

    @Autowired
    @Qualifier("defPusher")
    private Pusher pusher;

    public static void main(String[] args) {
        SpringApplication.run(PLApplicationStart.class, args);
        log.info("PLApplicationStart is ok");
    }

    @Override
    public void run(String... args) throws Exception {
        subscribeTopicMessage("consumerGroup", "AUDIT_HANDLE", "*", "172.31.14.122:9876;172.31.14.182:9876");
    }

    public static void subscribeTopicMessage(String consumerGroup, String topic, String subExpression, String addresses) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(addresses);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe(topic, subExpression);

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {
                    // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                    String msgContent = new String(msg.getBody());
                    int queueId = msg.getQueueId();
                    log.info("consumeThread=" + Thread.currentThread().getName() + "queueId=" + queueId + ", content:" + msgContent);
//                    DelayEvent delayEvent = JSONObject.parseObject(msgContent, DelayEvent.class);
//                    Long dataKV = delayEvent.getPenetrationDataKV("long", Long.class);
//                    TestAA key = delayEvent.getPenetrationDataKV("key", TestAA.class);
//                    // TestAA testAA = delayEvent.getPenetrationDataKV("key", TestAA.class);
//                   // System.out.println(testAA.getLongValue());
//                    System.out.println(key.getLongValue());
//                    System.out.println("111");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        log.info("topic :{} Consumer Started", topic);

    }

    @GetMapping("/transfer")
    public String transfer() {
        DelayEvent delayEvent = new DelayEvent();
        TestAA testAA = new TestAA();
        testAA.setInteger(1);
        testAA.setContent("content");
        testAA.setLongValue(22L);
        delayEvent.buildDelayTime(10L).
                buildRegistryName("defRegistry").
                buildPenetrationDataKV("key", testAA).
                buildPenetrationDataKV("业务key", "业务value").
                buildPenetrationDataKV("long", 11L).
                buildEventQueue("transfer").
                buildEventTag("aaaa");
        pusher.synSend(delayEvent);
        return "ok";
    }

    @GetMapping("/transfer2")
    public String transfer2() {
        TransferEvent2<String> event = new TransferEvent2<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给XXX30亿");
        pusher.synSend(event);
        return "ok";
    }

    @GetMapping("/transfer3")
    public String transfer3() {
        DelayEvent delayEvent = new DelayEvent();
        TestAA testAA = new TestAA();
        testAA.setInteger(1);
        testAA.setContent("content");
        testAA.setLongValue(22L);
        delayEvent.buildRegistryName("defRegistry").
                buildPenetrationDataKV("key", testAA).
                buildPenetrationDataKV("业务key", "业务value").
                buildPenetrationDataKV("long", 11L).
                buildEventQueue("AUDIT_HANDLE").setEventTag("FX_STRATEGY_CONFIG_AUDIT");
        pusher.synSend(delayEvent);
        return "ok";
    }

}
