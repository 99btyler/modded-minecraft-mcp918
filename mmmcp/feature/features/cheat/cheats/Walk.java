package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Walk extends Cheat {

    public Walk(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.LIVING_UPDATE);
    }

    @Override
    protected void onDisable() {

        minecraft.gameSettings.keyBindForward.pressed = false;

    }

    @Override
    protected void onEvent(Event event) {

        if (event.getEventType() == EventType.LIVING_UPDATE) {

            if (minecraft.currentScreen == null) {
                minecraft.gameSettings.keyBindForward.pressed = true;
            }

        }

    }

}
