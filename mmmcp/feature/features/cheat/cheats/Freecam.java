package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.event.events.EventSendPacket;
import mmmcp.feature.features.cheat.Cheat;
import mmmcp.util.Timer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.List;

public class Freecam extends Cheat {

    private EntityOtherPlayerMP entityOtherPlayerMP;

    private boolean wasSneakingBecauseSneakEnabled;
    private boolean wasSneakingBecauseSneaking;

    private final Timer timer;

    public Freecam(int keybind, boolean enabled) {

        super(keybind, enabled);

        timer = new Timer(300, 550);

    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
        eventNames.add("EventSendPacket");
    }

    @Override
    protected void onEnable() {

        if (!minecraft.thePlayer.onGround) {
            tryToggle(keybind);
            return;
        }

        minecraft.getMMMCP().tryToggleFeatures(false, "Jump", "Triggerbot", "Walk");

        if (minecraft.getMMMCP().getFeature("Sneak").isEnabled()) {
            wasSneakingBecauseSneakEnabled = true;
        } else if (minecraft.gameSettings.keyBindSneak.pressed) {
            wasSneakingBecauseSneaking = true;
        }

        entityOtherPlayerMP = new EntityOtherPlayerMP(minecraft.theWorld, minecraft.thePlayer.getGameProfile());

        entityOtherPlayerMP.copyLocationAndAnglesFrom(minecraft.thePlayer);
        entityOtherPlayerMP.rotationYawHead = minecraft.thePlayer.rotationYawHead;

        if (wasSneakingBecauseSneakEnabled) {
            minecraft.getMMMCP().tryToggleFeatures(false, "Sneak");
            entityOtherPlayerMP.setSneaking(true);
        } else if (wasSneakingBecauseSneaking) {
            minecraft.gameSettings.keyBindSneak.pressed = false;
            entityOtherPlayerMP.setSneaking(true);
        }

        minecraft.theWorld.addEntityToWorld(-928, entityOtherPlayerMP);

    }

    @Override
    protected void onDisable() {

        if (entityOtherPlayerMP == null) {
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

        minecraft.thePlayer.copyLocationAndAnglesFrom(entityOtherPlayerMP);
        minecraft.theWorld.removeEntityFromWorld(-928);
        entityOtherPlayerMP = null;

        if (wasSneakingBecauseSneakEnabled) {
            minecraft.getMMMCP().tryToggleFeatures(true, "Sneak");
        } else if (wasSneakingBecauseSneaking) {
            minecraft.gameSettings.keyBindSneak.pressed = true;
        }

        wasSneakingBecauseSneakEnabled = false;
        wasSneakingBecauseSneaking = false;

        minecraft.renderGlobal.loadRenderers();

    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                if (minecraft.currentScreen != null && !(minecraft.currentScreen instanceof GuiChat)) {
                    tryToggle(keybind);
                    return null;
                }

                minecraft.thePlayer.renderArmPitch = 1000;

                minecraft.thePlayer.noClip = true;

                minecraft.gameSettings.keyBindAttack.pressed = false;
                minecraft.gameSettings.keyBindUseItem.pressed = false;

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

                return null;

            case "EventSendPacket":

                final EventSendPacket eventSendPacket = (EventSendPacket)event;
                final Packet packet = eventSendPacket.getPacket();

                if (packet instanceof CPacketHeldItemChange) {

                    entityOtherPlayerMP.inventory.currentItem = ((CPacketHeldItemChange)packet).getSlotId();

                } else if (!(packet instanceof CPacketKeepAlive) && !(packet instanceof CPacketConfirmTransaction) && !(packet instanceof CPacketChatMessage) && !(packet instanceof CPacketTabComplete)) {
                    eventSendPacket.setCanceled(true);
                }

                if (timer.hasReached()) {

                    final CPacketPlayer.Position cPacketPlayerPosition = new CPacketPlayer.Position();
                    cPacketPlayerPosition.x = entityOtherPlayerMP.posX;
                    cPacketPlayerPosition.y = entityOtherPlayerMP.posY;
                    cPacketPlayerPosition.z = entityOtherPlayerMP.posZ;
                    cPacketPlayerPosition.yaw = entityOtherPlayerMP.rotationYaw;
                    cPacketPlayerPosition.pitch = entityOtherPlayerMP.rotationPitch;
                    cPacketPlayerPosition.onGround = entityOtherPlayerMP.onGround;

                    minecraft.thePlayer.connection.sendPacket(cPacketPlayerPosition);

                }

                return eventSendPacket;

            default:
                return null;

        }

    }

}
