package org.pl.mq.rocket.impl;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.pl.mq.api.Producer;

import java.util.Set;

public class RocketMqProducer implements Producer {

    private static volatile DefaultMQProducer producer;

    private static volatile DefaultMQAdminExt defaultMQAdminExt;

    private String nameServer;

    private String producerGroup;

    public RocketMqProducer() {
        if (producer == null) {
            synchronized (RocketMqProducer.class) {
                if (producer == null) {
                    producer = new DefaultMQProducer("DefProducerGroup");
                    producer.setNamesrvAddr("127.0.0.1:9876");
                    defaultMQAdminExt = new DefaultMQAdminExt();
                    defaultMQAdminExt.setNamesrvAddr("127.0.0.1:9876");
                    start();
                }
            }
        }
    }

    public RocketMqProducer(String nameServer, String producerGroup) {
        if (producer == null) {
            synchronized (RocketMqProducer.class) {
                if (producer == null) {
                    this.nameServer = nameServer;
                    this.producerGroup = producerGroup;
                    producer = new DefaultMQProducer(producerGroup);
                    producer.setNamesrvAddr(nameServer);
                    defaultMQAdminExt = new DefaultMQAdminExt();
                    defaultMQAdminExt.setNamesrvAddr(nameServer);
                    start();

                }
            }
        }

    }

    public void publishMessageSyn(String topic, String message) throws Exception {
        if (producer == null) {
            return;
        }

        Message msg = new Message(topic, "*", message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = producer.send(msg);
    }

    public Set<String> getTopicList() throws Exception {
        TopicList topicList = defaultMQAdminExt.fetchAllTopicList();
        return topicList.getTopicList();
    }


    /**
     * 对象在使用之前必须调用一次,并且只能初始化一次
     */
    public void start() {
        try {
            producer.start();
            defaultMQAdminExt.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文,使用上下文监听器,进行关闭
     */
    public void shutdown() {
        producer.shutdown();
    }
}
