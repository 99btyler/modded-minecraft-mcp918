package mmmcp.feature.features.screen.screens;

import mmmcp.feature.features.screen.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class ScreenGuiIngame extends Screen {

    public ScreenGuiIngame(int keybind, boolean enabled) {

        super(keybind, enabled);

        minecraft.ingameGUI = new ActualScreenGuiIngame(minecraft);

    }

    private class ActualScreenGuiIngame extends GuiIngame {

        public ActualScreenGuiIngame(Minecraft mcIn) {
            super(mcIn);
        }

        @Override
        public void renderGameOverlay(float partialTicks) {

            super.renderGameOverlay(partialTicks);

            if (!enabled) {
                return;
            }

            drawString(getFontRenderer(), minecraft.getMMMCP().getName(), 10, 10, 0xffffff);

        }

    }

}
