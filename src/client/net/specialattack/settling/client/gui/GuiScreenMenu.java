
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiScreenMenu extends GuiScreen {

    private GuiButton buttonResume;
    private GuiButton buttonDisconnect;

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.buttonResume.posX = newWidth / 2 - 200;
        this.buttonDisconnect.posX = newWidth / 2 - 200;
    }

    @Override
    public void onInit() {
        buttonResume = new GuiButton(LanguageRegistry.translate("gui.menu.resume"), this.width / 2 - 200, 350, 400, 50, this);
        buttonDisconnect = new GuiButton(LanguageRegistry.translate("gui.menu.disconnect"), this.width / 2 - 200, 410, 400, 50, this);

        this.elements.add(buttonResume);
        this.elements.add(buttonDisconnect);
    }

    @Override
    public void onRender(int mouseX, int mouseY) {
        TextureRegistry.getTexture("/textures/settling.png").bindTexture();

        GuiHelper.drawTexturedRectangle((float) width / 2.0F - 300, 40.0F, 600.0F, 300.0F, 0.0F, 0.0F, 1.0F, 1.0F);
    }

    @Override
    protected void onAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == buttonResume) {
            SettlingClient.instance.displayScreen(null);
        }
        if (element == buttonDisconnect) {
            SettlingClient.instance.currentWorld = null;
            SettlingClient.instance.markChunksDirty();
            SettlingClient.instance.displayScreen(null);
        }
    }

}
