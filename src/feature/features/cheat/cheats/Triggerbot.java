package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.util.Timer;

import java.util.List;

public class Triggerbot extends Cheat {

    private final Timer timer;

    public Triggerbot(int keybind, boolean enabled) {

        super(keybind, enabled);

        // 125-200 ms delay
        timer = new Timer(125, 200);

    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                // Automatically stop & disable self if any screen opens (chest, chat, inventory, etc)
                if (minecraft.currentScreen != null) {
                    tryToggle(keybind);
                    return null;
                }

                if (timer.hasReached()) {
                    minecraft.clickMouse();
                }

                return null;

            default:
                return null;

        }

    }

}
