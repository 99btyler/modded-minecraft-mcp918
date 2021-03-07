package mmmcp.feature.features.screen.screens;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import mmmcp.feature.features.screen.Screen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.Session;

import java.io.IOException;
import java.net.Proxy;

public class ScreenAccount extends Screen {

    public ScreenAccount(int keybind) {
        super(keybind);
    }

    @Override
    protected void onEnable() {

        // Enable
        minecraft.displayGuiScreen(new TheScreenAccount());

    }

    private class TheScreenAccount extends GuiScreen {

        private final int gtfWidth = 200;
        private final int gtfHeight = 20;

        private GuiTextField inputEmail;
        private GuiTextField inputPassword;

        @Override
        public void initGui() {
            inputEmail = new GuiTextField(0, fontRendererObj, (width / 2) - (gtfWidth / 2), (height / 2) - gtfHeight, gtfWidth, gtfHeight);
            inputPassword = new GuiTextField(0, fontRendererObj, (width / 2) - (gtfWidth / 2), (height / 2), gtfWidth, gtfHeight);
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            inputEmail.drawTextBox();
            inputPassword.drawTextBox();
            drawRect((width / 2) - (gtfWidth / 2), (height / 2), ((width / 2) - (gtfWidth / 2)) + gtfWidth, (height / 2) + gtfHeight, colorBackground);
        }

        @Override
        public void updateScreen() {
            inputEmail.updateCursorCounter();
            inputPassword.updateCursorCounter();
        }

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            inputEmail.mouseClicked(mouseX, mouseY, mouseButton);
            inputPassword.mouseClicked(mouseX, mouseY, mouseButton);
        }

        @Override
        protected void keyTyped(char typedChar, int keyCode) throws IOException {

            inputEmail.textboxKeyTyped(typedChar, keyCode);
            inputPassword.textboxKeyTyped(typedChar, keyCode);

            switch (keyCode) {

                // Disable
                case 1:
                    minecraft.displayGuiScreen(null);
                    toggle();
                    break;

                case 15:
                    if (!inputEmail.isFocused() && !inputPassword.isFocused()) {
                        inputEmail.setFocused(true);
                    } else {
                        inputEmail.setFocused(!inputEmail.isFocused());
                        inputPassword.setFocused(!inputPassword.isFocused());
                    }
                    break;

                case 28:

                    if (inputEmail.getText().isEmpty() && inputPassword.getText().isEmpty()) {
                        return;
                    }

                    final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
                    final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
                    authentication.setUsername(inputEmail.getText());
                    authentication.setPassword(inputPassword.getText());
                    try {
                        authentication.logIn();
                    } catch (AuthenticationException e) {
                        // Disable
                        minecraft.displayGuiScreen(null);
                        toggle();
                        break;
                    }

                    minecraft.session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");

                    minecraft.theWorld.sendQuittingDisconnectingPacket();
                    minecraft.loadWorld(null);
                    final boolean flag1 = minecraft.isIntegratedServerRunning();
                    final boolean flag2 = minecraft.func_181540_al();
                    if (flag1) {
                        minecraft.displayGuiScreen(new GuiMainMenu());
                    } else if (flag2) {
                        new RealmsBridge().switchToRealms(new GuiMainMenu());
                    } else {
                        minecraft.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                    }

                    break;

            }

        }

    }

}
