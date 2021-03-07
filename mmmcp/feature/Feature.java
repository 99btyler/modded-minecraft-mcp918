package mmmcp.feature;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public abstract class Feature {

    private final String name = getClass().getSimpleName();
    private boolean enabled = false;
    private final int keybind;

    private final List<EventType> eventTypes = new ArrayList<>();

    // For the Feature subclasses
    protected static final Minecraft minecraft = Minecraft.getMinecraft();

    public Feature(int keybind) {

        this.keybind = keybind;

        fillEventTypes(eventTypes);

    }

    public final String getName() {
        return name;
    }

    public String getTag() {
        return (enabled ? "§a" : "") + Keyboard.getKeyName(keybind).toLowerCase();
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final int getKeybind() {
        return keybind;
    }

    public final void toggle() {

        enabled = !enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }

    }

    protected void onEnable() {
        System.out.println(name + ".onEnable() wasn't overridden");
    }

    protected void onDisable() {
        System.out.println(name + ".onDisable() wasn't overridden");
    }

    protected void fillEventTypes(List<EventType> eventTypes) {
        System.out.println(name + ".fillEventTypes() wasn't overridden");
    }

    public final void tryOnEvent(Event event) {
        if (enabled && eventTypes.contains(event.getEventType())) {
            onEvent(event);
        }
    }

    protected void onEvent(Event event) {
        System.out.println(name + ".onEvent() wasn't overridden");
    }

}
