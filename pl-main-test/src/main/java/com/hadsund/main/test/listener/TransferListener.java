package com.hadsund.main.test.listener;

import com.hadsund.main.annotation.MainEventListener;
import com.hadsund.main.listener.Listener;
import com.hadsund.main.test.event.TransferEvent;
import com.hadsund.main.test.registry.TransactionRegistry;

@MainEventListener(registry = TransactionRegistry.class, desc = "转账用的")
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
