package com.hadsund.spring.test.event;

import com.hadsund.main.event.AbstractEvent;

import java.io.Serializable;

/**
 * @Author: wch
 * @Description: 测试进程间的
 * @Date: 2020/10/28 12:59 AM
 */
public class TransferEvent2<T> extends AbstractEvent implements Serializable {

    private static final long serialVersionUID = -4009507058134696499L;

    public String getRegistryName() {
        return "defRegistry";
    }

    public String eventQueue() {
        return "transfer";
    }

    public String eventUsed() {
        return EVENTINAPPLICATION;
    }

    public boolean isAsynHandle() {
        return false;
    }

    public String eventTag() {
        return "*";
    }

    public Long getDelayTime() {
        return 10L;
    }

    private String eventId;

    private String eventType;

    private T content;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }


}
