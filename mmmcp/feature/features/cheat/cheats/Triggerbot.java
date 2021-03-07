package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.util.Timer;

import java.util.List;

public class Triggerbot extends Cheat {

    private final Timer timer = new Timer(125, 200);

    public Triggerbot(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<String> eventTypes) {
        eventTypes.add(Event.livingUpdate);
    }

    @Override
    protected void onEvent(Event event) {

        if (event.getType().equals(Event.livingUpdate)) {

            if (minecraft.currentScreen != null) {
                toggle();
                return;
            }

            if (timer.hasReached()) {
                minecraft.clickMouse();
            }

        }

    }

}
