package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Jump extends Cheat {

    public Jump(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected void onDisable() {
        minecraft.gameSettings.keyBindJump.pressed = false;
    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                minecraft.gameSettings.keyBindJump.pressed = true;

                return null;

            default:
                return null;

        }

    }

}
