package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class Nametags extends Cheat {

    public Nametags(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    public final void doNametag(EntityPlayer entityPlayer, String entityPlayerName, double x, double y, double z, RenderManager renderManager, FontRenderer fontRenderer) {

        final double distance = minecraft.thePlayer.getDistanceToEntity(entityPlayer);
        entityPlayerName += "§r | " + (int)distance;

        final Scoreboard scoreboard = entityPlayer.getWorldScoreboard();
        final ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);
        if (scoreObjective != null) {
            final Score score = scoreboard.getOrCreateScore(entityPlayer.getName(), scoreObjective);
            renderLine(entityPlayer, (score.getScorePoints() + " " + scoreObjective.getDisplayName()), x, y, z, distance, renderManager, fontRenderer);
            y += (fontRenderer.FONT_HEIGHT * 1.15F * 0.025F) + (0.04 * distance);
        }

        renderLine(entityPlayer, entityPlayerName, x, y, z, distance, renderManager, fontRenderer);

    }

    private void renderLine(EntityPlayer entityPlayer, String entityPlayerName, double x, double y, double z, double distance, RenderManager renderManager, FontRenderer fontRenderer) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y + entityPlayer.height + 0.5 + (0.04 * distance), z);
        GlStateManager.rotate(-renderManager.playerViewY, 0, 1, 0);
        GlStateManager.rotate(((renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX), 1, 0, 0);
        GlStateManager.scale(-0.025 - (0.004 * distance), -0.025 - (0.004 * distance), 0.025);

        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        fontRenderer.drawString(entityPlayerName, -(fontRenderer.getStringWidth(entityPlayerName) / 2), 0, -1);

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();

    }

}
