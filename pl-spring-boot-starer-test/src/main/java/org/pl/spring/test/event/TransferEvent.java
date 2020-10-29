package org.pl.spring.test.event;

import org.pl.main.event.Event;

/**
 * @Author: wch
 * @Description: 测试进程内的
 * @Date: 2020/10/28 12:59 AM
 */
public class TransferEvent<T> implements Event{

    public String getRegistryName() {
        return "defRegistry";
    }

    public String eventQueue() {
        return "transfer";
    }

    public String eventUsed() {
        return EVENTINAPPLICATION;
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
