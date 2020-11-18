package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Hold extends Cheat {

    private int slotToHold;
    private boolean stoppedHolding;

    public Hold(int keybind, boolean enabled) {

        super(keybind, enabled);

        slotToHold = -1;
        stoppedHolding = true;
        // Because stoppedHolding is true, the first assumption will be to start holding

    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
        eventNames.add("EventRightClick");
    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                if (minecraft.thePlayer.inventory.currentItem == slotToHold) {

                    // Start holding
                    minecraft.gameSettings.keyBindUseItem.pressed = true;
                    stoppedHolding = false;

                } else {

                    // Make sure we haven't already stopped holding
                    if (!stoppedHolding) {

                        // Stop holding
                        minecraft.gameSettings.keyBindUseItem.pressed = false;
                        stoppedHolding = true;

                    }

                }

                return null;

            case "EventRightClick":

                if (slotToHold == -1) {
                    slotToHold = minecraft.thePlayer.inventory.currentItem;
                }

                return null;

            default:
                return null;

        }

    }

    @Override
    protected void onDisable() {
        slotToHold = -1;
        minecraft.gameSettings.keyBindUseItem.pressed = false;
    }

    @Override
    public String getTag() {
        return (minecraft.thePlayer.inventory.currentItem == slotToHold ? "§2" : "§c") + (slotToHold + 1);
    }

}
