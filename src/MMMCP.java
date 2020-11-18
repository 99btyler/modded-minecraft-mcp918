package mmmcp;

import mmmcp.feature.Feature;
import mmmcp.feature.features.cheat.cheats.*;
import mmmcp.feature.features.screen.screens.ScreenAccount;
import mmmcp.feature.features.screen.screens.ScreenGuiIngame;
import org.lwjgl.input.Keyboard;

public class MMMCP {

    private String name;

    private Feature[] features;

    public MMMCP() {

        // modded-minecraft-[mcp version]
        // mcp928 is for 1.9.4
        name = "modded-minecraft-mcp928";

        features = new Feature[] {

                // Cheats
                new Triggerbot(Keyboard.KEY_R, false),
                new Hold(Keyboard.KEY_N, false),
                new Nametags(Keyboard.KEY_I, false),
                new Outline(Keyboard.KEY_O, false),
                new Tracers(Keyboard.KEY_P, false),
                new Jump(Keyboard.KEY_J, false),
                new Walk(Keyboard.KEY_K, false),
                new InvMove(Keyboard.KEY_L, false),
                new Freecam(Keyboard.KEY_V, false),
                new Sneak(Keyboard.KEY_Z, false),
                new Sprint(Keyboard.KEY_C, false),

                // Screens
                new ScreenGuiIngame(Keyboard.KEY_RSHIFT, true),
                new ScreenAccount(Keyboard.KEY_RBRACKET, false)

        };

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

    public final void tryToggleFeatures(boolean desiredEnabled, String...featureNames) {
        for (String featureName : featureNames) {
            final Feature feature = getFeature(featureName);
            if (feature.isEnabled() != desiredEnabled) {
                feature.tryToggle(feature.getKeybind());
            }
        }
    }

}
