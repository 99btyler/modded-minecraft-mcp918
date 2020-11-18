package mmmcp.feature.event.events;

import mmmcp.feature.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {

    private Packet packet;
    private boolean canceled;

    public EventSendPacket(Packet packet) {
        this.packet = packet;
        canceled = false;
    }

    public final Packet getPacket() {
        return packet;
    }

    public final void setPacket(Packet packet) {
        this.packet = packet;
    }

    public final boolean isCanceled() {
        return canceled;
    }

    public final void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
