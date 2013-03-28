
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiButton;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiScreenMenu extends GuiScreen {

    private GuiButton buttonResume;
    private GuiButton buttonOptions;
    private GuiButton buttonDisconnect;

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.buttonResume.posX = newWidth / 2 - 200;
        this.buttonOptions.posX = newWidth / 2 - 200;
        this.buttonDisconnect.posX = newWidth / 2 - 200;
    }

    @Override
    public void onInit() {
        this.buttonResume = new GuiButton(LanguageRegistry.translate("gui.menu.resume"), this.width / 2 - 200, 300, 400, 50, this);
        this.buttonOptions = new GuiButton(LanguageRegistry.translate("gui.options"), this.width / 2 - 200, 360, 400, 50, this);
        this.buttonDisconnect = new GuiButton(LanguageRegistry.translate("gui.menu.disconnect"), this.width / 2 - 200, 420, 400, 50, this);

        this.elements.add(this.buttonResume);
        this.elements.add(this.buttonOptions);
        this.elements.add(this.buttonDisconnect);
    }

    @Override
    public void onRender(int mouseX, int mouseY) {}

    @SuppressWarnings("deprecation")
    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == this.buttonResume) {
            SettlingClient.instance.displayScreen(null);
        }
        if (element == this.buttonOptions) {
            SettlingClient.instance.displayScreen(new GuiOptions(this));
        }
        if (element == this.buttonDisconnect) {
            SettlingClient.instance.currentWorld = null;
            SettlingClient.instance.markChunksDirty();
            SettlingClient.instance.displayScreen(null);
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {
        // TODO Auto-generated method stub

    }

}
