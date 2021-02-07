package com.hadsund.main.push;

import com.hadsund.main.event.Event;
import com.hadsund.main.registry.Registry;

public interface Pusher {

    boolean synSend(Event event);

    void asynSend(Event event);

    boolean addRegistry(String registryName, Registry registry);

    boolean delRegistry(String registryName);
}
