package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.features.cheat.Cheat;

public class Bright extends Cheat {

    private float oldBrightness;

    public Bright(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    @Override
    protected void onEnable() {
        oldBrightness = minecraft.gameSettings.gammaSetting;
        minecraft.gameSettings.gammaSetting = 10F;
    }

    @Override
    protected void onDisable() {
        minecraft.gameSettings.gammaSetting = oldBrightness;
    }

}
