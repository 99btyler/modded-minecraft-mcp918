package mmmcp.feature.event;

public abstract class Event {

    private String name;

    public Event() {
        // Automatically set the event's name to whatever its class is named
        name = getClass().getSimpleName();
    }

    public final String getName() {
        return name;
    }

}
