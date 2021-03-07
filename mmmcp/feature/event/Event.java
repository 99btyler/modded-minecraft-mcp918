package mmmcp.feature.event;

import mmmcp.feature.event.details.EventType;

public abstract class Event {

    private final EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public final EventType getEventType() {
        return eventType;
    }

}
