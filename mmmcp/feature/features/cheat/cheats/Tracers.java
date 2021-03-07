package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
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
    protected void fillEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.RENDER_HAND);
    }

    @Override
    protected void onEvent(Event event) {

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        for (EntityPlayer entityPlayer : minecraft.theWorld.playerEntities) {

            if (entityPlayer == minecraft.thePlayer) {
                continue;
            }

            final int[] rgb = getRGBOfName(entityPlayer.getName(), entityPlayer.getDisplayName().getFormattedText());
            GL11.glColor4d(rgb[0], rgb[1], rgb[2], .60F - (.01F * minecraft.thePlayer.getDistanceToEntity(entityPlayer)));
            GL11.glLineWidth(.60F);

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

    private int[] getRGBOfName(String actualName, String formattedName) {

        int[] rgb = new int[] {1, 1, 1};

        final String preName = formattedName.split(actualName)[0];

        for (int i = preName.length() - 1; i >= 0; i--) {
            if (String.valueOf(preName.charAt(i)).equals("§")) {

                final String colorCode = "§" + preName.charAt(i + 1);

                if (colorCode.equals("§4") || colorCode.equals("§c")) {
                    rgb = new int[] {1, 0, 0};
                } else if (colorCode.equals("§2") || colorCode.equals("§a")) {
                    rgb = new int[] {0, 1, 0};
                } else if (colorCode.equals("§1") || colorCode.equals("§3") || colorCode.equals("§9") || colorCode.equals("§b")) {
                    rgb = new int[] {0, 0, 1};
                } else if (colorCode.equals("§6") || colorCode.equals("§e") || colorCode.equals("§g")) {
                    rgb = new int[] {1, 1, 0};
                }

            }
        }

        return rgb;

    }

}
