package mmmcp;

import mmmcp.feature.Feature;
import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.cheats.*;
import mmmcp.feature.features.screen.screens.ScreenAccount;
import mmmcp.feature.features.screen.screens.ScreenGuiIngame;
import org.lwjgl.input.Keyboard;

public class MMMCP {

    private final static MMMCP instance = new MMMCP();

    private final String name = "modded-minecraft-mcp918";

    private final Feature[] features = {

            // Cheats
            new Triggerbot(Keyboard.KEY_R),
            new Hold(Keyboard.KEY_M),
            new InvMove(Keyboard.KEY_I),
            new Nametags(Keyboard.KEY_O),
            new Tracers(Keyboard.KEY_P),
            new Jump(Keyboard.KEY_J),
            new Walk(Keyboard.KEY_K),
            new Freecam(Keyboard.KEY_V),
            new Sneak(Keyboard.KEY_Z),
            new Sprint(Keyboard.KEY_C),

            // Screens
            new ScreenGuiIngame(Keyboard.KEY_RSHIFT),
            new ScreenAccount(Keyboard.KEY_RBRACKET)

    };

    public final static MMMCP getInstance() {
        return instance;
    }

    public final String getName() {
        return name;
    }

    public final Feature[] getFeatures() {
        return features;
    }

    public final Feature getFeature(String featureName) {
        for (Feature feature : features) {
            if (feature.getName().equals(featureName)) {
                return feature;
            }
        }
        return null;
    }

    public final void ableFeatures(boolean desiredEnabled, String[] featureNames) {
        for (String featureName : featureNames) {
            final Feature feature = getFeature(featureName);
            if (feature.isEnabled() != desiredEnabled) {
                feature.toggle();
            }
        }
    }

    public final void alertFeatures(Event event) {
        for (Feature feature : features) {
            feature.tryOnEvent(event);
        }
    }

}
