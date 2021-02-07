package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Tracers extends Cheat {

    public Tracers(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventNames(List<String> eventNames) {
        eventNames.add("EventRenderHand");
    }

    @Override
    protected void onEvent(Event event) {

        if (event.getName().equals("EventRenderHand")) {

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);

            for (EntityPlayer entityPlayer : minecraft.theWorld.playerEntities) {

                if (entityPlayer == minecraft.thePlayer) {
                    continue;
                }

                GL11.glColor4d(1, 1, 1, .25F);
                GL11.glLineWidth(.25F);

                GL11.glBegin(GL11.GL_LINES);

                GL11.glVertex3d(entityPlayer.posX - minecraft.getRenderManager().renderPosX, (entityPlayer.posY + (entityPlayer.height / 2)) - minecraft.getRenderManager().renderPosY, entityPlayer.posZ - minecraft.getRenderManager().renderPosZ);
                final Vec3 vec3 = new Vec3(0, 0, 1).rotatePitch(-(float)Math.toRadians(minecraft.thePlayer.rotationPitch)).rotateYaw(-(float)Math.toRadians(minecraft.thePlayer.rotationYaw));
                GL11.glVertex3d(vec3.xCoord, vec3.yCoord + minecraft.thePlayer.getEyeHeight(), vec3.zCoord);

                GL11.glEnd();

            }

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

        }

    }

}
