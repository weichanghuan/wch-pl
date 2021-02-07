package com.hadsund.main.event;

import com.alibaba.fastjson.JSONObject;
import com.hadsund.main.callback.EventCallback;
import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wch
 * @Description: 延迟事件
 * @Date: 2020/11/27 1:04 PM
 */
public class DelayEvent extends AbstractEvent implements Serializable {

    private static final long serialVersionUID = 2974753501704146833L;

    private Map<String, Object> penetrationData = new HashMap<>();

    private String registryName;

    private boolean asynHandle = false;

    private String eventTag;

    private long delayTime = 0L;

    private String eventQueue;

    private final String eventUsed = EVENTAPPLICATIONROOM;

    public Map<String, Object> getPenetrationData() {
        return penetrationData;
    }

    public <T> T getPenetrationDataKV(String key, Class<T> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(this.penetrationData.get(key)), clazz);
    }

    public void setPenetrationData(Map<String, Object> penetrationData) {
        this.penetrationData = penetrationData;
    }

    public DelayEvent buildPenetrationDataKV(String key, Object value) {
        this.penetrationData.put(key, value);
        return this;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }

    public DelayEvent buildRegistryName(String registryName) {
        this.registryName = registryName;
        return this;
    }

    @Override
    public String getRegistryName() {
        return registryName;
    }

    public DelayEvent buildAsynHandle(boolean asynHandle) {
        this.asynHandle = asynHandle;
        return this;
    }

    public void setAsynHandle(boolean asynHandle) {
        this.asynHandle = asynHandle;
    }

    @Override
    public boolean isAsynHandle() {
        return asynHandle;
    }

    public DelayEvent buildEventTag(String eventTag) {
        this.eventTag = eventTag;
        return this;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public String getEventTag() {
        return eventTag;
    }

    @Override
    public String eventTag() {
        return eventTag;
    }

    public DelayEvent buildDelayTime(long delayTime) {
        this.delayTime = delayTime;
        return this;
    }


    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public Long getDelayTime() {
        return delayTime;
    }

    public DelayEvent buildEventQueue(String eventQueue) {
        this.eventQueue = eventQueue;
        return this;
    }

    public String getEventQueue() {
        return eventQueue;
    }

    public void setEventQueue(String eventQueue) {
        this.eventQueue = eventQueue;
    }

    public String getEventUsed() {
        return eventUsed;
    }


    @Override
    public String eventQueue() {
        return eventQueue;
    }

    @Override
    public String eventUsed() {
        return eventUsed;
    }
}
