package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.features.cheat.Cheat;

import java.util.List;

public class Sprint extends Cheat {

    public Sprint(int keybind) {
        super(keybind);
    }

    @Override
    protected void registerEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.LIVING_UPDATE);
    }

    @Override
    protected void onEvent(Event event) {

        minecraft.gameSettings.keyBindSprint.pressed = true;

    }

    @Override
    protected void onDisable() {

        minecraft.gameSettings.keyBindSprint.pressed = false;
        minecraft.thePlayer.setSprinting(false);

    }

}
