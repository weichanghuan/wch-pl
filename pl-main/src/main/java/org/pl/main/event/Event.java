package org.pl.main.event;

public interface Event {

    public static final String EVENTINAPPLICATION="in application";

    public static final String EVENTAPPLICATIONROOM="application romm";

    String getRegistryId();

    String eventQueue();

    String eventUsed();

}
