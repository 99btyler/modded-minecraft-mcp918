package mmmcp.feature.features.screen;

import mmmcp.feature.Feature;

public abstract class Screen extends Feature {

    protected final int colorBackground = 0xff202020;
    protected final int colorText = 0xFFFFFF;

    public Screen(int keybind) {
        super(keybind);
    }

}
