package com.hadsund.spring.config;

import com.alibaba.fastjson.JSONObject;
import com.hadsund.main.callback.EventCallback;
import com.hadsund.main.callback.impl.DefEventCallback;
import com.hadsund.main.constarct.EventContract;
import com.hadsund.main.event.DelayEvent;
import com.hadsund.main.event.Event;
import com.hadsund.main.push.Pusher;
import com.hadsund.spring.autoregister.HanderAnnotationListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/11/16 1:54 PM
 */
@Component
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(KeyExpiredListener.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("defPusher")
    private Pusher pusher;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            if (StringUtils.isBlank(key) || !key.contains(EventContract.delayeventPrefix)) {
                return;
            }
        } catch (Exception ex) {
            return;
        }
        String msg = new String(bytes, StandardCharsets.UTF_8);
        String lock = key + ".lock";
        Long increment = increment(lock);
        if (increment == null || increment > 1) {
            return;
        }

        pusher.synSend(initEvent(key));
        // 删除具体事件
        redisTemplate.boundHashOps(EventContract.delayevent).delete(key);
        redisTemplate.delete(lock);
    }

    private Event initEvent(String key) {
        String value = (String) redisTemplate.boundHashOps(EventContract.delayevent).get(key);
        DelayEvent delayEvent = JSONObject.parseObject(value, DelayEvent.class);
        if (delayEvent == null) {
            logger.error("select key is not find,plz check redis,key:{}", key);
            return null;
        }
        // 立即发送
        delayEvent.setDelayTime(0L);
        return delayEvent;
    }


    public Long increment(final String key) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            Long increment = operations.increment(key);
            return increment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
