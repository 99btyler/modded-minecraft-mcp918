package mmmcp.feature;

import mmmcp.feature.event.Event;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class Feature {

    protected final String name;

    protected int keybind;
    protected boolean enabled;

    private List<String> eventNames;

    protected final static Minecraft minecraft = Minecraft.getMinecraft();

    public Feature(int keybind, boolean enabled) {

        name = getClass().getSimpleName();

        this.keybind = keybind;
        this.enabled = enabled;

        eventNames = new ArrayList<>();
        setupEventNames(eventNames);

    }

    public final String getName() {
        return name;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void tryToggle(int keybind) {

        if (this.keybind != keybind) {
            return;
        }

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

    protected void setupEventNames(List<String> eventNames) {
        System.out.println(name + ".setupEventNames() wasn't overridden");
    }

    public final Event tryOnEvent(Event event) {
        if (enabled && eventNames.contains(event.getName())) {
            return onEvent(event);
        }
        return null;
    }

    protected Event onEvent(Event event) {
        System.out.println(name + ".onEvent() wasn't overridden");
        return null;
    }

}
