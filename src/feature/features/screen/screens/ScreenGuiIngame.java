package mmmcp.feature.features.screen.screens;

import mmmcp.MMMCP;
import mmmcp.feature.Feature;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.feature.features.screen.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScreenGuiIngame extends Screen {

    public ScreenGuiIngame(int keybind) {

        super(keybind);

        // Hijack minecraft.ingameGUI
        minecraft.ingameGUI = new TheScreenGuiIngame(minecraft);

    }

    private class TheScreenGuiIngame extends GuiIngame {

        private int currentX;
        private int currentY;
        private boolean alignLeft;

        public TheScreenGuiIngame(Minecraft mcIn) {
            super(mcIn);
        }

        @Override
        public void renderGameOverlay(float partialTicks) {

            super.renderGameOverlay(partialTicks);

            // Our stuff begins here

            if (!isEnabled()) {
                return;
            }

            // START AT TOP LEFT
            currentX = 5;
            currentY = 5;
            alignLeft = true;

            final String name = MMMCP.getInstance().getName();
            doBox(name);

            final String xyz = (int)minecraft.thePlayer.posX + "x " + (int)minecraft.thePlayer.posY + "y " + (int)minecraft.thePlayer.posZ + "z";
            doBox(xyz);

            final String fps = "FPS: " + minecraft.getDebugFPS();
            final String facing = minecraft.thePlayer.getHorizontalFacing().toString().toUpperCase();
            doBoxes(2, new String[] {fps, facing});

            final String time = new SimpleDateFormat("h:mma s").format(new Date());
            doBox(time);

            // MOVE TO TOP RIGHT
            currentX = new ScaledResolution(minecraft).getScaledWidth() - 5;
            currentY = 5;
            alignLeft = false;

            final List<String> cheatNames = new ArrayList<>();
            for (Feature feature : MMMCP.getInstance().getFeatures()) {
                if (feature instanceof Cheat) {
                    cheatNames.add(feature.getName() + " " + feature.getTag());
                }
            }
            doBoxes(3, cheatNames.toArray(new String[0]));

            currentY += 26;

            final List<String> potions = new ArrayList<>();
            for (PotionEffect potionEffect : minecraft.thePlayer.getActivePotionEffects()) {
                final String pName = I18n.format(potionEffect.getEffectName());
                final String pLevel = (potionEffect.getAmplifier() > 0 ? " " + I18n.format("enchantment.level." + String.valueOf(potionEffect.getAmplifier() + 1)) : "");
                final String pDuration = Potion.getDurationString(potionEffect);
                potions.add(pName + pLevel + " - " + pDuration);
            }
            doBoxes(1, potions.toArray(new String[0]));

        }

        private void doBox(String text) {

            makeBox(text);
            currentY += 13;

        }

        private void doBoxes(int maxPerLine, String[] texts) {

            final int startingX = currentX;

            for (int i = 0; i < texts.length; i+=maxPerLine) {

                if (alignLeft) {

                    for (int ii = 0; ii < maxPerLine; ii++) {
                        final int index = (i + ii);
                        if (index < texts.length) {

                            final String text = texts[index];
                            makeBox(text);
                            currentX += getFontRenderer().getStringWidth(text) + 5;

                        }
                    }

                } else {

                    for (int ii = (maxPerLine - 1); ii >= 0; ii--) {
                        final int index = (i + ii);
                        if (index < texts.length) {

                            final String text = texts[index];
                            makeBox(text);
                            currentX -= (getFontRenderer().getStringWidth(text) + 5);

                        }
                    }

                }

                currentX = startingX;
                currentY += 13;

            }

        }

        private void makeBox(String text) {

            if (alignLeft) {

                drawRect(currentX, currentY, currentX + getFontRenderer().getStringWidth(text) + 4, currentY + 12, colorBackground);
                drawString(getFontRenderer(), text, currentX + 2, currentY + 2, colorText);

            } else {

                drawRect(currentX, currentY, currentX - getFontRenderer().getStringWidth(text) - 4, currentY + 12, colorBackground);
                drawString(getFontRenderer(), text, currentX - getFontRenderer().getStringWidth(text) - 2, currentY + 2, colorText);

            }

        }

    }

}
