package com.hadsund.spring.test.listener;

import com.hadsund.main.listener.Listener;
import com.hadsund.spring.annotation.PLEventListener;
import com.hadsund.spring.registry.DefRegistry;
import com.hadsund.spring.test.event.TransferEvent;
import com.hadsund.spring.test.event.TransferEvent2;

@PLEventListener(registry = DefRegistry.class)
public class TransferListener2 extends Listener<TransferEvent2<String>> {

    public String matchEventQueue() {
        return "transfer";
    }

    public void listen(TransferEvent2<String> event) {
        System.out.println(event.getContent());
    }

    public boolean match(TransferEvent2<String> event) {
        if (matchEventQueue().equals(event.eventQueue())) {
            return true;
        }
        return false;
    }
}
