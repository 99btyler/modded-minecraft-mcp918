package mmmcp.feature.features.cheat.cheats;

import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiEditSign;
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

                if (minecraft.currentScreen instanceof GuiChat || minecraft.currentScreen instanceof GuiEditSign || minecraft.currentScreen instanceof GuiScreenBook || minecraft.currentScreen instanceof GuiRepair) {
                    return null;
                }

                KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindJump.getKeyCode(), (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()) || minecraft.getMMMCP().getFeature("Jump").isEnabled()));
                KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindForward.getKeyCode(), (Keyboard.isKeyDown(minecraft.gameSettings.keyBindForward.getKeyCode()) || minecraft.getMMMCP().getFeature("Walk").isEnabled()));

                return null;

            default:
                return null;

        }

    }

}
