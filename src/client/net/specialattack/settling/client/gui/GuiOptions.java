
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiButton;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiOptions extends GuiScreen {

    private GuiButton buttonReturn;
    private GuiButton buttonControls;
    private GuiButton buttonVideo;
    private GuiButton buttonLanguage;

    private GuiScreen parent;

    public GuiOptions(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    protected void onResize(int newWidth, int newHeight) {
        this.buttonControls.posX = newWidth / 2 - 360;
        this.buttonVideo.posX = newWidth / 2 + 10;
        this.buttonLanguage.posX = newWidth / 2 - 360;

        this.buttonReturn.posX = newWidth / 2 - 150;
    }

    @Override
    protected void onInit() {
        this.buttonControls = new GuiButton(LanguageRegistry.translate("gui.controls"), this.width / 2 - 360, 50, 350, 50, this);
        this.buttonVideo = new GuiButton(LanguageRegistry.translate("gui.video"), this.width / 2 + 10, 50, 350, 50, this);
        this.buttonLanguage = new GuiButton(LanguageRegistry.translate("gui.language"), this.width / 2 - 360, 110, 350, 50, this);

        this.elements.add(this.buttonControls);
        this.elements.add(this.buttonVideo);
        this.elements.add(this.buttonLanguage);

        this.buttonReturn = new GuiButton(LanguageRegistry.translate("gui.return"), this.width / 2 - 150, 170, 300, 50, this);
        this.elements.add(this.buttonReturn);
    }

    @Override
    protected void onRender(int mouseX, int mouseY) {}

    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == this.buttonReturn) {
            SettlingClient.instance.displayScreen(this.parent);
        }

        if (element == this.buttonControls) {
            SettlingClient.instance.displayScreen(new GuiControls(this));
        }
        if (element == this.buttonVideo) {
            SettlingClient.instance.displayScreen(new GuiVideoSettings(this));
        }
        if (element == this.buttonLanguage) {
            SettlingClient.instance.displayScreen(new GuiLanguage(this));
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {}

}
