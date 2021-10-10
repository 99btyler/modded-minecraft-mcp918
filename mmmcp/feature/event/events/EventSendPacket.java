package mmmcp.feature.event.events;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.Cancelable;
import mmmcp.feature.event.EventType;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event implements Cancelable {

    private Packet packet;

    private boolean canceled;

    public EventSendPacket(Packet packet) {

        super(EventType.SEND_PACKET);

        this.packet = packet;

    }

    public final Packet getPacket() {
        return packet;
    }

    public final void setPacket(Packet packet) {
        this.packet = packet;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
