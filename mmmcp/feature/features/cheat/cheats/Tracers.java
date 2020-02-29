package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Tracers extends Cheat {

    public Tracers(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    public final void doTracers() {

        for (EntityPlayer entityPlayer : minecraft.theWorld.playerEntities) {

            if (entityPlayer == minecraft.thePlayer) {
                continue;
            }

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);

            float[] color = new float[] {1.0F, 1.0F, 1.0F};
            final String entityPlayerName = entityPlayer.getDisplayName().getUnformattedText();
            if (entityPlayerName.contains("§4") || entityPlayerName.contains("§c")) {
                color = new float[] {1.0F, 0.0F, 0.0F};
            } else if (entityPlayerName.contains("§2") || entityPlayerName.contains("§a")) {
                color = new float[] {0.0F, 1.0F, 0.0F};
            } else if (entityPlayerName.contains("§1") || entityPlayerName.contains("§3") || entityPlayerName.contains("§9") || entityPlayerName.contains("§b")) {
                color = new float[] {0.0F, 0.0F, 1.0F};
            } else if (entityPlayerName.contains("§6") || entityPlayerName.contains("§e")) {
                color = new float[] {1.0F, 1.0F, 0.0F,};
            }
            if (entityPlayer.isInvisible()) {
                color = new float[] {1.0F, 0.0F, 1.0F};
            }
            GL11.glColor4d(color[0], color[1], color[2], 0.40F);
            GL11.glLineWidth(1.0F);

            GL11.glBegin(GL11.GL_LINES);
            final double entityPlayerX = entityPlayer.posX - minecraft.getRenderManager().renderPosX;
            final double entityPlayerY = (entityPlayer.posY + (entityPlayer.height / 2)) - minecraft.getRenderManager().renderPosY;
            final double entityPlayerZ = entityPlayer.posZ - minecraft.getRenderManager().renderPosZ;
            GL11.glVertex3d(entityPlayerX, entityPlayerY, entityPlayerZ);
            final Vec3d vec3d = new Vec3d(0, 0, 1).rotatePitch(-(float)Math.toRadians(minecraft.thePlayer.rotationPitch)).rotateYaw(-(float)Math.toRadians(minecraft.thePlayer.rotationYaw));
            GL11.glVertex3d(vec3d.xCoord, vec3d.yCoord + minecraft.thePlayer.getEyeHeight(), vec3d.zCoord);
            GL11.glEnd();

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

        }

    }

}
