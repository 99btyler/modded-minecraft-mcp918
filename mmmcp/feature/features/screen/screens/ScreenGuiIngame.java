package mmmcp.feature.features.screen.screens;

import mmmcp.feature.Feature;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.feature.features.screen.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ScreenGuiIngame extends Screen {

    public ScreenGuiIngame(int keybind, boolean enabled) {

        super(keybind, enabled);

        minecraft.ingameGUI = new ActualScreenGuiIngame(minecraft);

    }

    private class ActualScreenGuiIngame extends GuiIngame {

        private int currentX;
        private int currentY;
        private boolean shouldAlignLeft;

        public ActualScreenGuiIngame(Minecraft mcIn) {
            super(mcIn);
        }

        @Override
        public void renderGameOverlay(float partialTicks) {

            super.renderGameOverlay(partialTicks);

            if (!enabled) {
                return;
            }

            currentX = 5;
            currentY = 5;
            shouldAlignLeft = true;

            final String name = tagBD + (minecraft.getMMMCP().getName());
            doText(name);

            final String xyz = (minecraft.thePlayer.posY > 0 ? tagBD : tagBR) + ((int)minecraft.thePlayer.posX + "x " + (int)minecraft.thePlayer.posY + "y " + (int)minecraft.thePlayer.posZ + "z");
            doText(xyz);

            final String fps = (Minecraft.getDebugFPS() >= 60 ? tagBD : tagBR) + ("FPS: " + Minecraft.getDebugFPS());
            final String facing = tagBD + (minecraft.thePlayer.getHorizontalFacing().getName().toUpperCase());
            doSection(2, new String[] {fps, facing});

            final ScaledResolution scaledResolution = new ScaledResolution(minecraft);

            currentX = scaledResolution.getScaledWidth() - 5;
            currentY = 5;
            shouldAlignLeft = false;

            final List<String> cheatNames = new ArrayList<>();
            for (Feature feature : minecraft.getMMMCP().getFeatures()) {
                if (feature instanceof Cheat) {
                    cheatNames.add((feature.isEnabled() ?  tagBD : tagBDT) + (feature.getName() + (feature.isEnabled() ? (feature.getTag() != null ? (" [" + feature.getTag() + "§r]") : "") : "")));
                }
            }
            doSection(3, cheatNames.toArray(new String[0]));

            currentY += 13;

            final List<String> potions = new ArrayList<>();
            for (PotionEffect potionEffect : minecraft.thePlayer.getActivePotionEffects()) {
                final String pName = I18n.format(potionEffect.getPotion().getName());
                final String pLevel = (potionEffect.getAmplifier() > 0 ? I18n.format("enchantment.level." + (potionEffect.getAmplifier() + 1)) : "");
                final String pDuration = " | " + Potion.getPotionDurationString(potionEffect, 1.0F);
                potions.add((potionEffect.getPotion().isBeneficial() ? tagBD : tagBR) + (pName + pLevel + pDuration));
            }
            doSection(1, potions.toArray(new String[0]));

        }

        private void doText(String theText) {

            drawTheText(theText);

            currentY += 13;

        }

        private void doSection(int maxPerLine, String... theTexts) {

            final int startingCurrentX = currentX;

            for (int i = 0; i < theTexts.length; i+=maxPerLine) {

                if (shouldAlignLeft) {

                    for (int ii = 0; ii < maxPerLine; ii++) {
                        final int index = (i + ii);
                        if (index < theTexts.length) {
                            final String theText = theTexts[index];
                            drawTheText(theText);
                            currentX += (getFontRenderer().getStringWidth(getTextWithoutTag(theText)) + 5);
                        }
                    }

                } else {

                    for (int ii = (maxPerLine - 1); ii >= 0; ii--) {
                        final int index = (i + ii);
                        if (index < theTexts.length) {
                            final String theText = theTexts[index];
                            drawTheText(theText);
                            currentX -= (getFontRenderer().getStringWidth(getTextWithoutTag(theText)) + 5);
                        }
                    }

                }

                currentX = startingCurrentX;
                currentY += 13;

            }

        }

        private void drawTheText(String theText) {

            int colorBackground = 0xFFffffff;
            int colorText = colorTextDefault;

            final String tag = getTagFromText(theText);
            if (tag.equals(tagBD)) {
                colorBackground = colorBackgroundDefault;
            } else if (tag.equals(tagBDT)) {
                colorBackground = colorBackgroundDefaultTransparent;
                colorText = colorTextDefaultTransparent;
            } else if (tag.equals(tagBR)) {
                colorBackground = colorBackgroundRed;
            }

            final String textWithoutTag = getTextWithoutTag(theText);

            if (shouldAlignLeft) {

                drawRect(currentX, currentY, currentX + getFontRenderer().getStringWidth(textWithoutTag) + 4, currentY + 12, colorBackground);
                drawString(getFontRenderer(), textWithoutTag, currentX + 2, currentY + 2, colorText);

            } else {

                drawRect(currentX, currentY, currentX - getFontRenderer().getStringWidth(textWithoutTag) - 4, currentY + 12, colorBackground);
                drawString(getFontRenderer(), textWithoutTag, currentX - getFontRenderer().getStringWidth(textWithoutTag) - 4 + 2, currentY + 2, colorText);

            }

        }

    }

}
