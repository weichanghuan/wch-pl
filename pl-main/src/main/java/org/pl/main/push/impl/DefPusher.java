package org.pl.main.push.impl;

import org.apache.commons.lang3.StringUtils;
import org.pl.main.event.Event;
import org.pl.main.push.Pusher;
import org.pl.main.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 12:25 AM
 */
public class DefPusher implements Pusher {

    private static final Logger logger = LoggerFactory.getLogger(DefPusher.class);

    // 注册名称：注册中心
    private static Map<String, Registry> registryList = new ConcurrentHashMap<>();


    public DefPusher() {
    }

    synchronized public boolean addRegistry(String registryName, Registry registry) {
        if (StringUtils.isBlank(registryName)) {
            logger.error("addRegistry is fail ,registryName is null");
            return false;
        }

        if (registryList.containsKey(registryName)) {
            logger.error("addRegistry is fail ,registryName is repeat");
            return false;
        }

        registryList.put(registryName, registry);

        return true;
    }


    synchronized public boolean delRegistry(String registryName) {
        if (StringUtils.isBlank(registryName)) {
            logger.error("delRegistry is fail ,registryName is null");
            return false;
        }

        registryList.remove(registryName);

        return true;
    }

    public boolean synSend(Event event) {

        if (event == null || StringUtils.isBlank(event.eventQueue())) {
            logger.error("event is null or eventQueue is null");
            return false;
        }

        if (StringUtils.isBlank(event.eventUsed())) {
            logger.error("eventUsed is null ");
            return false;
        }

        String registryId = event.getRegistryName();
        Registry registry = registryList.get(registryId);
        if (registry == null) {
            return false;
        }

        try {
            registry.notifyAll(event);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public void asynSend(Event event) {
        CompletableFuture.runAsync(() -> {
            synSend(event);
        });
    }


}
