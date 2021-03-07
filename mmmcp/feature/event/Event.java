package mmmcp.feature.event;

public abstract class Event {

    // All types
    public static final String clickLeft = "EventClickLeft";
    public static final String clickRight = "EventClickRight";
    public static final String livingUpdate = "EventLivingUpdate";
    public static final String renderEntityName = "EventRenderEntityName";
    public static final String renderHand = "EventRenderHand";
    public static final String sendPacket = "EventSendPacket";

    // The event's type (set in subclass)
    private String type;

    public Event(String type) {
        this.type = type;
    }

    public final String getType() {
        return type;
    }

}
