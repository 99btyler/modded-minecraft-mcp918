package mmmcp.feature.event;

public abstract class Event {

    private final EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public final EventType getEventType() {
        return eventType;
    }

}
