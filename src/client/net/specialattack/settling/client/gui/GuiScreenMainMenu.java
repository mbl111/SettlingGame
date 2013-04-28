
package net.specialattack.settling.client.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiButton;
import net.specialattack.settling.client.gui.element.GuiButtonDonate;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.client.world.WorldDemo;
import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiScreenMainMenu extends GuiScreen {

    private GuiButton buttonPlay;
    private GuiButton buttonOptions;
    private GuiButton buttonExit;
    private GuiButtonDonate buttonDonate;

    //private GuiTextbox serverAddress;

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.buttonPlay.posX = newWidth / 2 - 200;
        this.buttonOptions.posX = newWidth / 2 - 200;
        this.buttonExit.posX = newWidth / 2 - 200;
        this.buttonDonate.posX = newWidth - 200;
        this.buttonDonate.posY = newHeight - 50;
    }

    @Override
    public void onInit() {
        this.buttonPlay = new GuiButton(LanguageRegistry.translate("gui.mainscreen.play"), this.width / 2 - 200, 300, 400, 50, this);
        this.buttonOptions = new GuiButton(LanguageRegistry.translate("gui.options"), this.width / 2 - 200, 360, 400, 50, this);
        this.buttonExit = new GuiButton(LanguageRegistry.translate("gui.mainscreen.exit"), this.width / 2 - 200, 420, 400, 50, this);
        this.buttonDonate = new GuiButtonDonate(LanguageRegistry.translate("gui.mainscreen.donate"), this.width - 200, this.height - 50, 200, 50, this);
        //serverAddress = new GuiTextbox(this.width / 2 - 200, 200, 400, 50, this);

        this.elements.add(this.buttonPlay);
        this.elements.add(this.buttonOptions);
        this.elements.add(this.buttonExit);
        this.elements.add(this.buttonDonate);
        //this.elements.add(serverAddress);
    }

    @Override
    public void drawBackground() {
        // TODO: create fancy background, low priority
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        TextureRegistry.getTexture("/textures/settling.png").bindTexture();

        GuiHelper.drawTexturedRectangle((float) this.width / 2.0F - 300, 40.0F, 600.0F, 250.0F, 0.0F, 0.0F, 1.0F, 1.0F);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == this.buttonPlay) {
            SettlingClient.instance.currentWorld = new WorldDemo(null); // XXX: Not the way this will be done
            SettlingClient.instance.markChunksDirty();
            SettlingClient.instance.displayScreen(null);
        }
        if (element == this.buttonOptions) {
            SettlingClient.instance.displayScreen(new GuiOptions(this));
        }
        if (element == this.buttonExit) {
            Settling.getInstance().attemptShutdown();
        }
        if (element == this.buttonDonate) {
            try {
                Desktop.getDesktop().browse(URI.create("http://www.specialattack.net/donations"));
            }
            catch (IOException e) {}
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {}

}
