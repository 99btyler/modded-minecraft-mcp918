package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Sneak extends Cheat {

    public Sneak(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected void onDisable() {

        minecraft.gameSettings.keyBindSneak.pressed = false;

    }

    @Override
    protected void onEvent(Event event) {

        if (event.getName().equals("EventLivingUpdate")) {

            minecraft.gameSettings.keyBindSneak.pressed = true;

        }

    }

}
