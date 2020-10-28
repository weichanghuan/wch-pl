package org.pl.main.registry;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.pl.main.event.Event;
import org.pl.main.listener.Listener;
import org.pl.mq.api.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 12:04 AM
 */
public abstract class Registry {

    private static final Logger logger = LoggerFactory.getLogger(Registry.class);

    private Producer producer;

    private String packageName;

    private static Map<String, Listener> listenerMap = new ConcurrentHashMap<String, Listener>();

    synchronized public boolean addListener(Listener listener) {
        String id = getListenerId();
        if (StringUtils.isNotBlank(id)) {
            listenerMap.put(id, listener);
            return true;
        }
        return false;

    }

    synchronized public boolean deleteListener(Listener listener) {
        String id = getListenerId();
        if (StringUtils.isNotBlank(id)) {
            listenerMap.remove(id);
            return true;
        }
        return false;
    }


   public abstract String getListenerId();


    public void notifyAll(Event event) {
        // 区分进程内核进程间的
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
            if (entry.getValue().match(event)) {
                entry.getValue().listen(event);
            }
        }
    }

    private void notifyApplicationRoom(Event event) {
        if (producer == null) {
            logger.error("Registry producer is null");
            return;
        }

        // 直接发布
        List<String> topicList = null;
        try {
            topicList = producer.getTopicList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        if (CollectionUtils.isEmpty(topicList)) {
            return;
        }
        boolean flag = false;
        for (String topic : topicList) {
            if (topic.equals(event.eventQueue())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            try {
                producer.publishMessageSyn(event.eventQueue(), JSON.toJSONString(event));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
