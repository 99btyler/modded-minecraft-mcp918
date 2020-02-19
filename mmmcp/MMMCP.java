package mmmcp;

import mmmcp.feature.Feature;
import mmmcp.feature.features.cheat.cheats.Jump;
import mmmcp.feature.features.cheat.cheats.Sprint;
import mmmcp.feature.features.cheat.cheats.Walk;
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

}
