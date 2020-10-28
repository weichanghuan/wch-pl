package org.pl.main.event;

public interface Event {

    String EVENTINAPPLICATION="in application";

    String EVENTAPPLICATIONROOM="application romm";

    String getRegistryId();

    String eventQueue();

    String eventUsed();

}
