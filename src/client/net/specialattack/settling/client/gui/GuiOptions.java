
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiOptions extends GuiScreen {

    private GuiButton buttonReturn;
    private GuiButton buttonControls;
    private GuiButton buttonVideo;

    private GuiScreen parent;

    public GuiOptions(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    protected void onResize(int newWidth, int newHeight) {
        buttonControls.posX = newWidth / 2 - 310;

        buttonReturn.posX = newWidth / 2 - 150;
    }

    @Override
    protected void onInit() {
        buttonControls = new GuiButton(LanguageRegistry.translate("gui.controls"), width / 2 - 310, 50, 300, 50, this);
        buttonVideo = new GuiButton(LanguageRegistry.translate("gui.video"), width / 2 + 10, 50, 300, 50, this);

        this.elements.add(buttonControls);
        this.elements.add(buttonVideo);

        buttonReturn = new GuiButton(LanguageRegistry.translate("gui.return"), width / 2 - 150, 110, 300, 50, this);
        this.elements.add(buttonReturn);
    }

    @Override
    protected void onRender(int mouseX, int mouseY) {}

    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == buttonReturn) {
            SettlingClient.instance.displayScreen(parent);
        }

        if (element == buttonControls) {
            SettlingClient.instance.displayScreen(new GuiControls(this));
        }
        if (element == buttonVideo) {
            SettlingClient.instance.displayScreen(null);
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

}
