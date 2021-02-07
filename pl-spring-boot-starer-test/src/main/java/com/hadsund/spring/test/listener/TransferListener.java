package com.hadsund.spring.test.listener;

import com.hadsund.main.event.Event;
import com.hadsund.main.listener.Listener;
import com.hadsund.spring.annotation.PLEventListener;
import com.hadsund.spring.registry.DefRegistry;
import com.hadsund.spring.test.event.TransferEvent;

@PLEventListener(registry = DefRegistry.class)
public class TransferListener extends Listener<TransferEvent<String>> {

    public String matchEventQueue() {
        return "transfer";
    }

    public void listen(TransferEvent<String> event) {
        System.out.println(event.getContent());
    }

    public boolean match(TransferEvent<String> event) {
        if (matchEventQueue().equals(event.eventQueue())) {
            return true;
        }
        return false;
    }
}
