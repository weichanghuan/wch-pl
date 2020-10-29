package org.pl.spring.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.pl.main.push.Pusher;
import org.pl.spring.test.event.TransferEvent;
import org.pl.spring.test.event.TransferEvent2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication(scanBasePackages = "org.pl.spring",exclude = {DataSourceAutoConfiguration.class})
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
        subscribeTopicMessage("111","transfer","*","172.31.10.20:9876;172.31.10.217:9876");
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

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        log.info("topic :{} Consumer Started", topic);

    }

    @GetMapping("/transfer")
    public String transfer() {
        TransferEvent<String> event = new TransferEvent<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给危常焕30亿");
        pusher.synSend(event);
        return "ok";
    }

    @GetMapping("/transfe2")
    public String transfer2() {
        TransferEvent2<String> event = new TransferEvent2<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给危常焕30亿");
        pusher.synSend(event);
        return "ok";
    }

}
