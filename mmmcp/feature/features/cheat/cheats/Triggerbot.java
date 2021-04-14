package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.util.Timer;

import java.util.List;

public class Triggerbot extends Cheat {

    private final Timer timer = new Timer(125, 200);

    public Triggerbot(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.LIVING_UPDATE);
    }

    @Override
    protected void onEvent(Event event) {

        if (minecraft.currentScreen != null) {
            toggle();
            return;
        }

        if (timer.hasReachedDelay()) {
            minecraft.clickMouse();
        }

    }

}
