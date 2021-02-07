package mmmcp.feature.event;

public abstract class Event {

    private final String name = getClass().getSimpleName();

    public final String getName() {
        return name;
    }

}
