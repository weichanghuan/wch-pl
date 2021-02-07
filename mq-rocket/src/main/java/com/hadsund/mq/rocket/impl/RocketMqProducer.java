package com.hadsund.mq.rocket.impl;

import com.alibaba.fastjson.JSON;
import com.hadsund.mq.api.callback.MQSendCallback;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import com.hadsund.mq.api.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RocketMqProducer implements Producer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqProducer.class);

    private static volatile DefaultMQProducer producer;

    private static volatile DefaultMQAdminExt defaultMQAdminExt;

    private String nameServer;

    private String producerGroup;

    public RocketMqProducer() {
        if (producer == null) {
            synchronized (RocketMqProducer.class) {
                if (producer == null) {
                    producer = new DefaultMQProducer("DefProducerGroup");
                    producer.setNamesrvAddr("localhost:9876");
                    defaultMQAdminExt = new DefaultMQAdminExt();
                    defaultMQAdminExt.setNamesrvAddr("localhost:9876");
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

    public void publishMessageSyn(String topic, String message, String tag, MQSendCallback mqSendCallback) throws Exception {
        if (producer == null) {
            return;
        }

        Message msg = new Message(topic, tag, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        try {
            SendResult send = producer.send(msg, 5000L);
            if (send != null && send.getSendStatus() == SendStatus.SEND_OK) {
                mqSendCallback.onSuccess(message);
            } else {
                logger.error("SendResult:{}，message:{}", JSON.toJSONString(send), message);
            }
        } catch (Exception ex) {
            mqSendCallback.onException(ex, message);
        }

    }

    public Set<String> getTopicList() throws Exception {
        logger.info("init rocketmq nameServer is {}", nameServer);
        String[] split = nameServer.split(";");
        List<String> names = defaultMQAdminExt.getNameServerAddressList();
        if (CollectionUtils.isEmpty(names)) {
            return null;
        }
        boolean flag = false;
        for (String name : names) {
            for (String s : split) {
                if (s.equals(name)) {
                    flag = true;
                    break;
                }
            }


        }
        if (!flag) {
            Properties properties = new Properties();
            properties.setProperty("1", "1");
            defaultMQAdminExt.updateNameServerConfig(properties, Arrays.asList(split));
        }

        TopicList topicList = defaultMQAdminExt.fetchAllTopicList();
        List<String> nameServerAddressList = defaultMQAdminExt.getNameServerAddressList();
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
