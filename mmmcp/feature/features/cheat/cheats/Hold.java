package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Hold extends Cheat {

    private int slotToHold = -1;
    private boolean stoppedHolding = true;

    public Hold(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.CLICK_LEFT);
        eventTypes.add(EventType.CLICK_RIGHT);
        eventTypes.add(EventType.LIVING_UPDATE);
    }

    @Override
    protected void onDisable() {

        slotToHold = -1;
        minecraft.gameSettings.keyBindUseItem.pressed = false;

    }

    @Override
    protected void onEvent(Event event) {

        switch (event.getEventType()) {

            case CLICK_LEFT:
                toggle();
                break;

            case CLICK_RIGHT:

                if (slotToHold == -1) {
                    slotToHold = minecraft.thePlayer.inventory.currentItem;
                }

                break;

            case LIVING_UPDATE:

                if (minecraft.thePlayer.inventory.currentItem == slotToHold) {

                    minecraft.gameSettings.keyBindUseItem.pressed = true;
                    stoppedHolding = false;

                } else {

                    if (!stoppedHolding) {
                        minecraft.gameSettings.keyBindUseItem.pressed = false;
                        stoppedHolding = true;
                    }

                }

                break;

        }

    }

    @Override
    public String getTag() {
        return super.getTag() + (isEnabled() && slotToHold != -1 ? "§r " + (minecraft.thePlayer.inventory.currentItem == slotToHold ? "§a" : "§c") + (slotToHold + 1) : "");
    }

}
