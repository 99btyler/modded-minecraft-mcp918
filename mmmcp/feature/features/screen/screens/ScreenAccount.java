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

    public ScreenAccount(int keybind, boolean enabled) {
        super(keybind, enabled);
    }

    @Override
    protected void onEnable() {
        minecraft.displayGuiScreen(new ActualScreenAccount());
    }

    private class ActualScreenAccount extends GuiScreen {

        private GuiTextField guiTextFieldEmail;
        private GuiTextField guiTextFieldPassword;

        private String message;

        @Override
        public void initGui() {
            guiTextFieldEmail = new GuiTextField(0, fontRendererObj, (width / 2) - 90, (((height / 2) - 10) - 10) - 2, 180, 20);
            guiTextFieldPassword = new GuiTextField(1, fontRendererObj, (width / 2) - 90, (((height / 2) - 10) + 10) + 2, 180, 20);
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {

            guiTextFieldEmail.drawTextBox();

            guiTextFieldPassword.drawTextBox();
            if (!guiTextFieldPassword.getText().equals("§7Password")) {
                drawRect((width / 2) - 90, (((height / 2) - 10) + 10) + 2, ((width / 2) - 90) + 180, ((((height / 2) - 10) + 10) + 2) + 20, colorBackgroundDefault);
            }

            drawString(fontRendererObj, message, (width / 2) - 90, (((height / 2) - 10) - 10) - 15, colorTextDefault);

        }

        @Override
        public void updateScreen() {

            guiTextFieldEmail.updateCursorCounter();
            updateFocus(guiTextFieldEmail, "§7Email");

            guiTextFieldPassword.updateCursorCounter();
            updateFocus(guiTextFieldPassword, "§7Password");

            message = "";
            if (!guiTextFieldEmail.isFocused() && !guiTextFieldPassword.isFocused()) {
                message = "Press TAB";
            } else if (!guiTextFieldEmail.getText().contains("§7") && (!guiTextFieldPassword.getText().contains("§7Password") && !guiTextFieldPassword.getText().isEmpty())) {
                message = "Press ENTER";
            }

        }

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            guiTextFieldEmail.mouseClicked(mouseX, mouseY, mouseButton);
            guiTextFieldPassword.mouseClicked(mouseX, mouseY, mouseButton);
        }

        @Override
        protected void keyTyped(char typedChar, int keyCode) throws IOException {

            guiTextFieldEmail.textboxKeyTyped(typedChar, keyCode);
            guiTextFieldPassword.textboxKeyTyped(typedChar, keyCode);

            switch (keyCode) {

                case 1:
                    minecraft.displayGuiScreen(null);
                    tryToggle(keybind);
                    break;

                case 15:
                    if (!guiTextFieldEmail.isFocused() && !guiTextFieldPassword.isFocused()) {
                        guiTextFieldEmail.setFocused(true);
                    } else {
                        guiTextFieldEmail.setFocused(!guiTextFieldEmail.isFocused());
                        guiTextFieldPassword.setFocused(!guiTextFieldPassword.isFocused());
                    }
                    break;

                case 28:

                    if (!message.equals("Press ENTER")) {
                        break;
                    }

                    final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");

                    final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
                    authentication.setUsername(guiTextFieldEmail.getText());
                    authentication.setPassword(guiTextFieldPassword.getText());
                    try {
                        authentication.logIn();
                    } catch (AuthenticationException e) {
                        guiTextFieldEmail.setText("");
                        guiTextFieldEmail.setFocused(false);
                        guiTextFieldPassword.setText("");
                        guiTextFieldPassword.setFocused(false);
                        break;
                    }

                    minecraft.session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");

                    minecraft.theWorld.sendQuittingDisconnectingPacket();
                    minecraft.loadWorld(null);
                    final boolean flag1 = minecraft.isIntegratedServerRunning();
                    final boolean flag2 = minecraft.isConnectedToRealms();
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

        private void updateFocus(GuiTextField guiTextField, String defaultText) {

            if (guiTextField.isFocused()) {
                if (guiTextField.getText().equals(defaultText)) {
                    guiTextField.setText("");
                }
            } else {
                if (guiTextField.getText().isEmpty()) {
                    guiTextField.setText(defaultText);
                }
            }

        }

    }

}
