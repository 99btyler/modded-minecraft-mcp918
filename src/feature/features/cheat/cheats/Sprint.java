package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Sprint extends Cheat {

    public Sprint(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected void onDisable() {

        minecraft.gameSettings.keyBindSprint.pressed = false;
        minecraft.thePlayer.setSprinting(false);

    }

    @Override
    protected void onEvent(Event event) {

        if (event.getName().equals("EventLivingUpdate")) {

            minecraft.gameSettings.keyBindSprint.pressed = true;

        }

    }

}
