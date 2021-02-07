package mmmcp.feature.features.cheat.cheats;

import mmmcp.MMMCP;
import mmmcp.feature.event.Event;
import mmmcp.feature.features.cheat.Cheat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class InvMove extends Cheat {

    public InvMove(int keybind) {
        super(keybind);
    }

    @Override
    protected void fillEventNames(List<String> eventNames) {
        eventNames.add("EventLivingUpdate");
    }

    @Override
    protected void onEvent(Event event) {

        if (event.getName().equals("EventLivingUpdate")) {

            if (!(minecraft.currentScreen instanceof GuiInventory)) {
                return;
            }

            KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindForward.getKeyCode(), (Keyboard.isKeyDown(minecraft.gameSettings.keyBindForward.getKeyCode()) || MMMCP.getInstance().getFeature("Walk").isEnabled()));
            KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindJump.getKeyCode(), (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode()) || MMMCP.getInstance().getFeature("Jump").isEnabled()));

        }

    }

}
