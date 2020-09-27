package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class InvMove extends Cheat {

    public InvMove(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    @Override
    protected void setupEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected Event onEvent(Event event) {

        switch (event.getName()) {

            case "EventLivingUpdate":

                // Make sure inventory is open
                if (!(minecraft.currentScreen instanceof GuiInventory)) {
                    return null;
                }

                // Force Minecraft to acknowledge keyBindForward and keyBindJump
                KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindForward.getKeyCode(), (Keyboard.isKeyDown(minecraft.gameSettings.keyBindForward.getKeyCode()) || minecraft.getMMMCP().getFeature("Walk").isEnabled()));
                KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindJump.getKeyCode(), (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()) || minecraft.getMMMCP().getFeature("Jump").isEnabled()));

                return null;

            default:
                return null;

        }

    }

}
