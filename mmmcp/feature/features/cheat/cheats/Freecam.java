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

    private EntityOtherPlayerMP clone;

    private boolean wasSneakingBecauseSneakEnabled;
    private boolean wasSneakingBecauseSneaking;

    private final Timer cPacketPlayerPositionTimer;

    public Freecam(int keybind, boolean enabled) {

        super(keybind, enabled);

        // 300-550 ms delay
        cPacketPlayerPositionTimer = new Timer(300, 550);

    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
        eventNames.add("EventSendPacket");
    }

    @Override
    protected void onEnable() {

        // Player must be on ground before starting freecam
        if (!minecraft.thePlayer.onGround) {
            tryToggle(keybind);
            return;
        }

        // Disable incompatible cheats
        minecraft.getMMMCP().tryToggleFeatures(false, "Jump", "Triggerbot", "Walk");

        // If player is sneaking, understand how
        if (minecraft.getMMMCP().getFeature("Sneak").isEnabled()) {
            wasSneakingBecauseSneakEnabled = true;
        } else if (minecraft.gameSettings.keyBindSneak.pressed) {
            wasSneakingBecauseSneaking = true;
        }

        // Create player's clone (will be used for spoofing packets)
        clone = new EntityOtherPlayerMP(minecraft.theWorld, minecraft.thePlayer.getGameProfile());
        clone.copyLocationAndAnglesFrom(minecraft.thePlayer);
        clone.rotationYawHead = minecraft.thePlayer.rotationYawHead;
        if (wasSneakingBecauseSneakEnabled) {
            minecraft.getMMMCP().tryToggleFeatures(false, "Sneak");
            clone.setSneaking(true);
        } else if (wasSneakingBecauseSneaking) {
            minecraft.gameSettings.keyBindSneak.pressed = false;
            clone.setSneaking(true);
        }
        minecraft.theWorld.addEntityToWorld(-999, clone);

    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                // Automatically stop & disable self if a screen opens (chest, inventory, etc)
                if (minecraft.currentScreen != null && !(minecraft.currentScreen instanceof GuiChat)) {
                    tryToggle(keybind);
                    return null;
                }

                minecraft.thePlayer.renderArmPitch = 1000;
                minecraft.gameSettings.keyBindAttack.pressed = false;
                minecraft.gameSettings.keyBindUseItem.pressed = false;

                // Player movement (freecam)
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

                return null;

            case "EventSendPacket":

                final EventSendPacket eventSendPacket = (EventSendPacket)event;
                final Packet packet = eventSendPacket.getPacket();

                if (packet instanceof CPacketHeldItemChange) {
                    clone.inventory.currentItem = ((CPacketHeldItemChange)packet).getSlotId();
                } else if (!(packet instanceof CPacketKeepAlive) && !(packet instanceof CPacketConfirmTransaction) && !(packet instanceof CPacketChatMessage) && !(packet instanceof CPacketTabComplete)) {
                    eventSendPacket.setCanceled(true);
                }

                // Occasionally send position packets to make it seem like player is standing where player's clone is
                if (cPacketPlayerPositionTimer.hasReached()) {
                    final CPacketPlayer.Position cPacketPlayerPosition = new CPacketPlayer.Position();
                    cPacketPlayerPosition.x = clone.posX;
                    cPacketPlayerPosition.y = clone.posY;
                    cPacketPlayerPosition.z = clone.posZ;
                    cPacketPlayerPosition.yaw = clone.rotationYaw;
                    cPacketPlayerPosition.pitch = clone.rotationPitch;
                    cPacketPlayerPosition.onGround = clone.onGround;
                    minecraft.thePlayer.connection.sendPacket(cPacketPlayerPosition);
                }

                return eventSendPacket;

            default:
                return null;

        }

    }

    @Override
    protected void onDisable() {

        if (clone == null) {
            return;
        }

        // Stop movement
        minecraft.gameSettings.keyBindForward.pressed = false;
        minecraft.gameSettings.keyBindBack.pressed = false;
        minecraft.gameSettings.keyBindRight.pressed = false;
        minecraft.gameSettings.keyBindLeft.pressed = false;
        minecraft.gameSettings.keyBindJump.pressed = false;
        minecraft.gameSettings.keyBindSneak.pressed = false;
        minecraft.thePlayer.motionX = 0;
        minecraft.thePlayer.motionY = 0;
        minecraft.thePlayer.motionZ = 0;

        // Send player back to starting spot (where player's clone is)
        minecraft.thePlayer.copyLocationAndAnglesFrom(clone);
        minecraft.theWorld.removeEntityFromWorld(-999);
        clone = null;
        if (wasSneakingBecauseSneakEnabled) {
            minecraft.getMMMCP().tryToggleFeatures(true, "Sneak");
        } else if (wasSneakingBecauseSneaking) {
            minecraft.gameSettings.keyBindSneak.pressed = true;
        }

        wasSneakingBecauseSneakEnabled = false;
        wasSneakingBecauseSneaking = false;
        minecraft.renderGlobal.loadRenderers();

    }

}
