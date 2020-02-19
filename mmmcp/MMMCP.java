package mmmcp;

import mmmcp.feature.Feature;
import mmmcp.feature.features.cheat.cheats.*;
import mmmcp.feature.features.screen.screens.ScreenGuiIngame;
import org.lwjgl.input.Keyboard;

public class MMMCP {

    private String name;

    private Feature[] features;

    public MMMCP() {

        name = "modded-minecraft-mcp928";

        features = new Feature[] {

                new Jump(Keyboard.KEY_J, false),
                new Walk(Keyboard.KEY_K, false),
                new InvMove(Keyboard.KEY_L, false),
                new Sneak(Keyboard.KEY_Z, false),
                new Sprint(Keyboard.KEY_C, false),

                new ScreenGuiIngame(Keyboard.KEY_RSHIFT, true)

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

}
