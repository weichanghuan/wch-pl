package com.hadsund.main.registry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hadsund.main.callback.EventCallback;
import com.hadsund.main.constarct.EventContract;
import com.hadsund.main.event.Event;
import com.hadsund.main.listener.Listener;
import com.hadsund.mq.api.callback.MQSendCallback;
import com.hadsund.mq.api.callback.impl.DefMQSendCallback;
import org.apache.commons.lang3.StringUtils;
import com.hadsund.mq.api.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 12:04 AM
 */
public abstract class Registry {

    private static final Logger logger = LoggerFactory.getLogger(Registry.class);

    private Producer producer;

    private RedisTemplate redisTemplate;

    private static Map<String, Listener> listenerMap = new ConcurrentHashMap<String, Listener>();

    public abstract String getListenerId();

    public abstract String getRegistryName();

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    synchronized public void addListener(Listener listener) {
        String id = getListenerId();
        if (StringUtils.isNotBlank(id)) {
            listenerMap.put(id, listener);
        }
    }

    synchronized public boolean deleteListener(Listener listener) {
        String id = getListenerId();
        if (StringUtils.isNotBlank(id)) {
            listenerMap.remove(id);
            return true;
        }
        return false;
    }


    public void notifyAll(Event event) {
        // 区分进程内进程间的
        if (Event.EVENTINAPPLICATION.equals(event.eventUsed())) {
            notifyApplicationIn(event);
            return;
        }
        if (Event.EVENTAPPLICATIONROOM.equals(event.eventUsed())) {
            notifyApplicationRoom(event);
        }
    }

    private void notifyApplicationIn(Event event) {
        Set<Map.Entry<String, Listener>> entries = listenerMap.entrySet();
        for (Map.Entry<String, Listener> entry : entries) {
            String eventType = ((Type) event.getClass()).getTypeName();
            String type = entry.getValue().getEventType().getTypeName();
            if (type.contains("<")) {
                type = type.substring(0, type.indexOf("<"));
            }
            if (!eventType.equals(type)) {
                continue;
            }

            if (event.isAsynHandle()) {
                CompletableFuture.supplyAsync(() -> {
                    entry.getValue().handleListen(event);
                    return true;
                });
            } else {
                entry.getValue().handleListen(event);
            }
        }
    }

    private void notifyApplicationRoom(Event event) {
        if (null == producer) {
            logger.error("Registry {} producer is null", this);
            return;
        }
        publishMessageSyn(event);

    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    private void publishMessageSyn(Event event) {
        try {
            // 若是延迟发送,则注册redis
            Long delayTime = event.getDelayTime();
            if (delayTime > 0) {
                logger.info("dalayEvent delayTime:{} topic:{},content:{}", delayTime, event.eventQueue(), JSON.toJSONString(event));
                // 延迟发送
                delaySend(event);
                return;
            }
            logger.info("event send topic:{},content:{}", event.eventQueue(), JSON.toJSONString(event));
            if (StringUtils.isBlank(event.eventTag())) {
                producer.publishMessageSyn(event.eventQueue(), JSON.toJSONString(event), "*", initMQSendCallback(event));
            } else {
                producer.publishMessageSyn(event.eventQueue(), JSON.toJSONString(event), event.eventTag(), initMQSendCallback(event));
            }

        } catch (Exception ex) {
            logger.error("publishMessageSyn is fail topic:{},content:{}", event.eventQueue(), JSON.toJSONString(event));
            ex.printStackTrace();
        }
    }

    private void delaySend(Event event) {
        if (null == redisTemplate) {
            logger.error("redisTemplate is null,delayMsg is fail topic:{},content:{}", event.eventQueue(), JSON.toJSONString(event));
            return;
        }
        String value = JSON.toJSONString(event);
        // 发布延迟事件id  delayEvent-UUID
        String uuid = getUUID();
        redisTemplate.opsForValue().set(EventContract.delayeventPrefix + uuid, value, event.getDelayTime(), TimeUnit.SECONDS);
        redisTemplate.boundHashOps(EventContract.delayevent).put(EventContract.delayeventPrefix + uuid, value);
    }

    private MQSendCallback initMQSendCallback(Event event) {
        EventCallback eventCallBack = event.getEventCallBack();
        if (eventCallBack == null) {
            return new DefMQSendCallback();
        }

        return new MQSendCallback() {
            @Override
            public void onSuccess(String message) {
                Event resultEvent = JSON.parseObject(message, Event.class);
                eventCallBack.onSuccess(resultEvent);
            }

            @Override
            public void onException(Throwable e, String message) {
                Event resultEvent = JSON.parseObject(message, Event.class);
                eventCallBack.onException(e, resultEvent);
            }
        };
    }


    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }


}
