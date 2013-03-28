
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiButton;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.client.gui.element.GuiLanguageList;
import net.specialattack.settling.client.gui.element.GuiScreen;
import net.specialattack.settling.client.util.Settings;
import net.specialattack.settling.common.lang.LanguageRegistry;

public class GuiLanguage extends GuiScreen {

    private GuiButton buttonReturn;
    private GuiLanguageList list;

    private GuiScreen parent;

    public GuiLanguage(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void keyPressed(int key, char character) {}

    @Override
    public void onResize(int newWidth, int newHeight) {
        buttonReturn.posX = newWidth / 2 - 150;
        list.posX = width / 2 - 250;
    }

    @Override
    public void onInit() {
        list = new GuiLanguageList(width / 2 - 250, 100, 500, 14 * 20, this);
        buttonReturn = new GuiButton(LanguageRegistry.translate("gui.done"), width / 2 - 150, height - 80, 300, 50, this);

        for (String element : LanguageRegistry.getLanguages().keySet()) {
            list.add(element);
        }

        this.elements.add(buttonReturn);
        this.elements.add(list);
    }

    @Override
    public void onRender(int mouseX, int mouseY) {}

    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == buttonReturn) {
            SettlingClient.instance.displayScreen(parent);
            LanguageRegistry.loadLang(list.selectedIndex);
        }

        if (element == list) {
            LanguageRegistry.loadLang(list.selectedIndex);
            buttonReturn.label = LanguageRegistry.translate("gui.done");
            Settings.saveSettings();
        }

    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {}

}
