package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Hold extends Cheat {

    private int slotToHold = -1;
    private boolean stoppedHolding = true;

    public Hold(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<String> eventTypes) {
        eventTypes.add(Event.clickLeft);
        eventTypes.add(Event.clickRight);
        eventTypes.add(Event.livingUpdate);
    }

    @Override
    protected void onDisable() {

        slotToHold = -1;
        minecraft.gameSettings.keyBindUseItem.pressed = false;

    }

    @Override
    protected void onEvent(Event event) {

        switch (event.getType()) {

            case Event.clickLeft:
                toggle();
                break;

            case Event.clickRight:

                if (slotToHold == -1) {
                    slotToHold = minecraft.thePlayer.inventory.currentItem;
                }

                break;

            case Event.livingUpdate:

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
