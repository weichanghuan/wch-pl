package org.pl.main.test.listener;

import org.pl.main.annotation.MainEventListener;
import org.pl.main.event.Event;
import org.pl.main.listener.Listener;
import org.pl.main.test.event.TransferEvent;
import org.pl.main.test.registry.TransactionRegistry;

@MainEventListener(registry = TransactionRegistry.class)
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
