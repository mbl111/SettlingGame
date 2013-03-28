
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiButton;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.client.gui.element.GuiList;
import net.specialattack.settling.client.util.Settings;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiLanguage extends GuiScreen {

    private GuiButton buttonReturn;
    private GuiList list;

    private GuiScreen parent;

    public GuiLanguage(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void keyPressed(int key, char character) {}

    @Override
    public void onResize(int newWidth, int newHeight) {
        this.buttonReturn.posX = newWidth / 2 - 150;
        this.buttonReturn.posY = newHeight - 80;

        this.list.posX = newWidth / 2 - 250;
        this.list.height = newHeight - 220;
        this.list.reCalculate();
    }

    @Override
    public void onInit() {
        this.list = new GuiList(this.width / 2 - 250, 100, 500, this.height - 220, this);
        this.buttonReturn = new GuiButton(LanguageRegistry.translate("gui.done"), this.width / 2 - 150, this.height - 80, 300, 50, this);

        for (String element : LanguageRegistry.getLanguages().keySet()) {
            this.list.add(element);
        }

        this.elements.add(this.buttonReturn);
        this.elements.add(this.list);
    }

    @Override
    public void onRender(int mouseX, int mouseY) {}

    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == this.buttonReturn) {
            SettlingClient.instance.displayScreen(this.parent);
            LanguageRegistry.loadLang(this.list.selectedIndex);
        }

        if (element == this.list) {
            LanguageRegistry.loadLang(this.list.selectedIndex);
            this.buttonReturn.label = LanguageRegistry.translate("gui.done");
            Settings.saveSettings();
        }

    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {}

}
