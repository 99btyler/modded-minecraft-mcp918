package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Sneak extends Cheat {

    public Sneak(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.LIVING_UPDATE);
    }

    @Override
    protected void onEvent(Event event) {

        minecraft.gameSettings.keyBindSneak.pressed = true;

    }

    @Override
    protected void onDisable() {

        minecraft.gameSettings.keyBindSneak.pressed = false;

    }

}
