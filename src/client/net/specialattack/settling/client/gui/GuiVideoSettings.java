
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiButton;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.client.gui.element.GuiToggleButton;
import net.specialattack.settling.client.util.ScreenResolution;
import net.specialattack.settling.client.util.Settings;
import net.specialattack.settling.common.lang.LanguageRegistry;

import org.lwjgl.opengl.DisplayMode;

public class GuiVideoSettings extends GuiScreen {

    private GuiButton buttonReturn;

    private GuiButton buttonFullscreen;
    private GuiToggleButton<DisplayMode> buttonResolution;

    private GuiScreen parent;

    public GuiVideoSettings(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    protected void onResize(int newWidth, int newHeight) {
        this.buttonFullscreen.posX = newWidth / 2 - 360;
        this.buttonResolution.posX = newWidth / 2 + 10;

        this.buttonReturn.posX = newWidth / 2 - 150;
    }

    @Override
    protected void onInit() {
        boolean fullscreen = Settings.fullscreen.getState();
        this.buttonFullscreen = new GuiButton(LanguageRegistry.translate("gui.video.fullscreen", fullscreen ? "gui.on" : "gui.off"), this.width / 2 - 360, 50, 350, 50, this);
        this.buttonResolution = new GuiToggleButton<DisplayMode>(this.width / 2 + 10, 50, 350, 50, this);

        for (DisplayMode displayMode : ScreenResolution.getDisplayModes()) {
            this.buttonResolution.addOption(ScreenResolution.getReadableDisplayMode(displayMode), displayMode);
        }

        this.buttonResolution.setValue(ScreenResolution.getReadableDisplayMode(Settings.displayMode.getMode()));

        this.elements.add(this.buttonFullscreen);
        this.elements.add(this.buttonResolution);

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

        if (element == this.buttonFullscreen) {
            boolean fullscreen = Settings.fullscreen.toggleState();
            this.buttonFullscreen.label = LanguageRegistry.translate("gui.video.fullscreen", fullscreen ? "gui.on" : "gui.off");

            SettlingClient.instance.setFullscreen(fullscreen);

            Settings.saveSettings();
        }
        if (element == this.buttonResolution) {
            Settings.displayMode.setMode(this.buttonResolution.selected);

            SettlingClient.instance.updateFullscreen();

            Settings.saveSettings();
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {}

}
