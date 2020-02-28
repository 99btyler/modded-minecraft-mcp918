package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class Outline extends Cheat {

    public Outline(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected void onDisable() {

        for (EntityPlayer entityPlayer : minecraft.theWorld.playerEntities) {
            if (entityPlayer != minecraft.thePlayer) {
                entityPlayer.setGlowing(false);
            }
        }

    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                for (EntityPlayer entityPlayer : minecraft.theWorld.playerEntities) {
                    if (entityPlayer != minecraft.thePlayer) {
                        entityPlayer.setGlowing(true);
                    }
                }

                return null;

            default:
                return null;

        }

    }

}
