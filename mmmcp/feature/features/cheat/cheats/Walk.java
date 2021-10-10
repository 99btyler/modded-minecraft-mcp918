package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.EventType;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Walk extends Cheat {

    public Walk(int keybind) {
        super(keybind);
    }

    @Override
    protected void registerEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.LIVING_UPDATE);
    }

    @Override
    protected void onEvent(Event event) {

        if (minecraft.currentScreen != null) {
            return;
        }

        minecraft.gameSettings.keyBindForward.pressed = true;

    }

    @Override
    protected void onDisable() {

        minecraft.gameSettings.keyBindForward.pressed = false;

    }

}
