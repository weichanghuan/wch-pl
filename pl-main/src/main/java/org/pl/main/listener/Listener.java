package org.pl.main.listener;

import org.pl.main.event.Event;

public abstract class Listener {

    public abstract String matchEventQueue();

    public abstract void listen(Event event);

    public boolean match(Event event) {
        if (event.eventQueue().equals(matchEventQueue())) {
            return true;
        }
        return false;

    }


}
