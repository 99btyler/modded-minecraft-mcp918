package mmmcp.feature.features.cheat.cheats;

import mmmcp.MMMCP;
import mmmcp.feature.event.Event;
import mmmcp.feature.event.details.EventType;
import mmmcp.feature.event.events.EventSendPacket;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.util.Timer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.List;

public class Freecam extends Cheat {

    private EntityOtherPlayerMP clone;

    // 0 = Sneak.isEnabled()
    // 1 = keyBindSneak.pressed
    private final boolean[] wasSneaking = new boolean[2];

    private final Timer timer = new Timer(950, 1050);

    public Freecam(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventTypes(List<EventType> eventTypes) {
        eventTypes.add(EventType.LIVING_UPDATE);
        eventTypes.add(EventType.SEND_PACKET);
    }

    @Override
    protected void onEvent(Event event) {

        switch (event.getEventType()) {

            case LIVING_UPDATE:

                if (minecraft.currentScreen != null && !(minecraft.currentScreen instanceof GuiChat)) {
                    toggle();
                    return;
                }

                minecraft.thePlayer.renderArmPitch = 1000f;
                minecraft.gameSettings.keyBindAttack.pressed = false;
                minecraft.gameSettings.keyBindUseItem.pressed = false;

                minecraft.thePlayer.noClip = true;
                if (minecraft.gameSettings.keyBindForward.pressed) {
                    minecraft.thePlayer.motionX *= 2;
                    minecraft.thePlayer.motionY = 0;
                    minecraft.thePlayer.motionZ *= 2;
                } else {
                    minecraft.thePlayer.motionX = 0;
                    minecraft.thePlayer.motionY = 0;
                    minecraft.thePlayer.motionZ = 0;
                }
                if (minecraft.gameSettings.keyBindJump.pressed) {
                    minecraft.thePlayer.motionY += 1.5;
                } else if (minecraft.gameSettings.keyBindSneak.pressed) {
                    minecraft.thePlayer.motionY -= 1.5;
                }

                break;

            case SEND_PACKET:

                final EventSendPacket eventSendPacket = (EventSendPacket)event;
                final Packet packet = eventSendPacket.getPacket();

                if (packet instanceof C09PacketHeldItemChange) {
                    clone.inventory.currentItem = ((C09PacketHeldItemChange)packet).getSlotId();
                } else if (!(packet instanceof C00PacketKeepAlive) && !(packet instanceof C0FPacketConfirmTransaction) & !(packet instanceof C01PacketChatMessage) & !(packet instanceof C14PacketTabComplete)) {
                    eventSendPacket.setCanceled(true);
                }

                if (timer.hasReached()) {
                    final C03PacketPlayer.C04PacketPlayerPosition position = new C03PacketPlayer.C04PacketPlayerPosition();
                    position.x = clone.posX;
                    position.y = clone.posY;
                    position.z = clone.posZ;
                    minecraft.thePlayer.sendQueue.addToSendQueue(position);
                }

                break;

        }

    }

    @Override
    protected void onEnable() {

        if (!minecraft.thePlayer.onGround) {
            toggle();
            return;
        }

        clone = new EntityOtherPlayerMP(minecraft.theWorld, minecraft.thePlayer.getGameProfile());
        clone.copyLocationAndAnglesFrom(minecraft.thePlayer);
        clone.setRotationYawHead(minecraft.thePlayer.rotationYawHead);

        MMMCP.getInstance().ableFeatures(false, new String[] {"Jump", "Triggerbot", "Walk"});

        if (MMMCP.getInstance().getFeature("Sneak").isEnabled()) {
            wasSneaking[0] = true;
            MMMCP.getInstance().getFeature("Sneak").toggle();
            clone.setSneaking(true);
        } else if (minecraft.gameSettings.keyBindSneak.pressed) {
            wasSneaking[1] = true;
            minecraft.gameSettings.keyBindSneak.pressed = false;
            clone.setSneaking(true);
        }

        minecraft.theWorld.addEntityToWorld(-999, clone);

    }

    @Override
    protected void onDisable() {

        if (clone == null) {
            return;
        }

        minecraft.gameSettings.keyBindForward.pressed = false;
        minecraft.gameSettings.keyBindBack.pressed = false;
        minecraft.gameSettings.keyBindRight.pressed = false;
        minecraft.gameSettings.keyBindLeft.pressed = false;
        minecraft.gameSettings.keyBindJump.pressed = false;
        minecraft.gameSettings.keyBindSneak.pressed = false;
        minecraft.thePlayer.motionX = 0;
        minecraft.thePlayer.motionY = 0;
        minecraft.thePlayer.motionZ = 0;

        if (wasSneaking[0]) {
            MMMCP.getInstance().ableFeatures(true, new String[] {"Sneak"});
        } else if (wasSneaking[1]) {
            minecraft.gameSettings.keyBindSneak.pressed = true;
        }
        wasSneaking[0] = false;
        wasSneaking[1] = false;

        minecraft.thePlayer.copyLocationAndAnglesFrom(clone);
        minecraft.thePlayer.setRotationYawHead(clone.rotationYawHead);
        minecraft.theWorld.removeEntityFromWorld(-999);
        clone = null;

        minecraft.renderGlobal.loadRenderers();

    }

}
