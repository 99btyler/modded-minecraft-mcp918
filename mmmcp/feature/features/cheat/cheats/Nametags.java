package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.event.events.EventRenderEntityName;
import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.util.List;

public class Nametags extends Cheat {

    public Nametags(int keybind) {
        super(keybind);
    }

    @Override
    protected void registerEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.RENDER_ENTITY_NAME);
    }

    @Override
    protected void onEvent(Event event) {

        final EventRenderEntityName eventRenderEntityName = (EventRenderEntityName)event;
        eventRenderEntityName.setCanceled(true);

        final RenderManager renderManager = eventRenderEntityName.getRenderManager();
        final FontRenderer fontRenderer = renderManager.getFontRenderer();

        final EntityPlayer entityPlayer = eventRenderEntityName.getEntityPlayer();
        final double x = eventRenderEntityName.getX();
        double y = eventRenderEntityName.getY();
        final double z = eventRenderEntityName.getZ();

        final double distance = minecraft.thePlayer.getDistanceToEntity(entityPlayer);

        // SCORE
        final Scoreboard scoreboard = entityPlayer.getWorldScoreboard();
        final ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);
        if (scoreObjective != null) {
            final Score score = scoreboard.getValueFromObjective(entityPlayer.getName(), scoreObjective);
            doLine(renderManager, fontRenderer, entityPlayer, (score.getScorePoints() + " " + scoreObjective.getDisplayName()), distance, x, y, z);
            y += (fontRenderer.FONT_HEIGHT * 1.15F * 0.025F) + (0.04 * distance);
        }

        // NAME
        doLine(renderManager, fontRenderer, entityPlayer, (entityPlayer.getDisplayName().getFormattedText() + "§r | " + (int)distance), distance, x, y, z);

    }

    private void doLine(RenderManager renderManager, FontRenderer fontRenderer, EntityPlayer entityPlayer, String text, double distance, double x, double y, double z) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y + entityPlayer.height + 0.5 + (0.04 * distance), z);
        GlStateManager.rotate(-renderManager.playerViewY, 0, 1, 0);
        GlStateManager.rotate(((renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX), 1, 0, 0);
        GlStateManager.scale(-0.025 - (0.004 * distance), -0.025 - (0.004 * distance), 0.025);

        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();

        fontRenderer.drawString(text, -(fontRenderer.getStringWidth(text) / 2), 0, -1);

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();

    }

}
