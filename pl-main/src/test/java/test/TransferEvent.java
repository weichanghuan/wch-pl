package test;

import org.pl.main.event.Event;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 12:59 AM
 */
public class TransferEvent<T> implements Event{

    @Override
    public String getRegistryId() {
        return "付款注册中心";
    }

    @Override
    public String eventQueue() {
        return "transfer";
    }

    @Override
    public String eventUsed() {
        return EVENTAPPLICATIONROOM;
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
