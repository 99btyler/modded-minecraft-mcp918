package mmmcp.feature;

import mmmcp.feature.event.Event;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public abstract class Feature {

    private final String name = getClass().getSimpleName();
    private boolean enabled = false;
    private final int keybind;

    private final List<String> eventNames = new ArrayList<>();

    // For the Feature subclasses
    protected final static Minecraft minecraft = Minecraft.getMinecraft();

    public Feature(int keybind) {
        this.keybind = keybind;
        fillEventNames(eventNames);
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

    protected void fillEventNames(List<String> eventNames) {
        System.out.println(name + ".addEventNames() wasn't overridden");
    }

    public final void tryOnEvent(Event event) {
        if (enabled && eventNames.contains(event.getName())) {
            onEvent(event);
        }
    }

    protected void onEvent(Event event) {
        System.out.println(name + ".onEvent() wasn't overridden");
    }

}
