package org.pl.main.push;

import org.pl.main.event.Event;
import org.pl.main.registry.Registry;

public interface Pusher {

    boolean synSend(Event event);

    void asynSend(Event event);

    boolean addRegistry(String registryName, Registry registry);

    boolean delRegistry(String registryName);
}
