package com.hadsund.main.test.event;

import com.hadsund.main.event.AbstractEvent;
import com.hadsund.main.event.Event;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 12:59 AM
 */
public class TransferEvent<T> extends AbstractEvent {

    private static final long serialVersionUID = 8301201349211375179L;

    public String getRegistryName() {
        return "TransactionRegistry";
    }

    public String eventQueue() {
        return "transfer";
    }

    public String eventUsed() {
        return EVENTINAPPLICATION;
    }

    @Override
    public boolean isAsynHandle() {
        return true;
    }

    @Override
    public String eventTag() {
        return "*";
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
