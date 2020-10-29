package org.pl.spring.test.listener;

import org.pl.main.event.Event;
import org.pl.main.listener.Listener;
import org.pl.spring.annotation.PLEventListener;
import org.pl.spring.registry.DefRegistry;
import org.pl.spring.test.event.TransferEvent;

@PLEventListener(registry = DefRegistry.class)
public class TransferListener extends Listener {

    @Override
    public String matchEventQueue() {
        return "transfer";
    }

    @Override
    public void listen(Event event) {
        TransferEvent<String> transferEvent = null;
        if (event instanceof TransferEvent) {
            transferEvent = (TransferEvent<String>) event;
        }
        String content = transferEvent.getContent();
        System.out.println(content);
    }
}
