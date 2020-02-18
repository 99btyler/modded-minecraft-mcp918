package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Sprint extends Cheat {

    public Sprint(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected void onDisable() {
        minecraft.gameSettings.keyBindSprint.pressed = false;
        minecraft.thePlayer.setSprinting(false);
    }

    @Override
    protected void onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                minecraft.gameSettings.keyBindSprint.pressed = true;

                break;

            default:
                break;

        }

    }

}
