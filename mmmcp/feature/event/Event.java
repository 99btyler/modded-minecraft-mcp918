package mmmcp.feature.event;

public abstract class Event {

    private String name;

    public Event() {
        name = getClass().getSimpleName();
    }

    public final String getName() {
        return name;
    }

}
