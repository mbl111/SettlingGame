
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
    private GuiButton buttonGrabMouse;

    private GuiScreen parent;

    public GuiVideoSettings(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    protected void onResize(int newWidth, int newHeight) {
        int i = 0;

        this.buttonFullscreen.posX = newWidth / 2 - 360 + 370 * (i++ % 2);
        this.buttonResolution.posX = newWidth / 2 - 360 + 370 * (i++ % 2);
        this.buttonGrabMouse.posX = newWidth / 2 - 360 + 370 * (i++ % 2);

        this.buttonReturn.posX = newWidth / 2 - 150;
    }

    @Override
    protected void onInit() {
        int i = 0;

        boolean fullscreen = Settings.fullscreen.getState();
        this.buttonFullscreen = new GuiButton(LanguageRegistry.translate("gui.video.fullscreen", fullscreen ? "gui.on" : "gui.off"), this.width / 2 - 360 + 370 * (i % 2), 50 + (i++ / 2) * 60, 350, 50, this);
        this.buttonResolution = new GuiToggleButton<DisplayMode>(this.width / 2 - 360 + 370 * (i % 2), 50 + (i++ / 2) * 60, 350, 50, this);

        for (DisplayMode displayMode : ScreenResolution.getDisplayModes()) {
            this.buttonResolution.addOption(ScreenResolution.getReadableDisplayMode(displayMode), displayMode);
        }
        this.buttonResolution.setValue(ScreenResolution.getReadableDisplayMode(Settings.displayMode.getMode()));

        boolean grabMouse = Settings.grabMouse.getState();
        this.buttonGrabMouse = new GuiButton(LanguageRegistry.translate("gui.video.grabMouse", grabMouse ? "gui.on" : "gui.off"), this.width / 2 - 360 + 370 * (i % 2), 50 + (i++ / 2) * 60, 350, 50, this);

        this.elements.add(this.buttonFullscreen);
        this.elements.add(this.buttonResolution);
        this.elements.add(this.buttonGrabMouse);

        i++;

        this.buttonReturn = new GuiButton(LanguageRegistry.translate("gui.return"), this.width / 2 - 150, 50 + ((i + 0) / 2) * 60, 300, 50, this);
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
        if (element == this.buttonGrabMouse) {
            boolean grab = Settings.grabMouse.toggleState();
            this.buttonGrabMouse.label = LanguageRegistry.translate("gui.video.grabMouse", grab ? "gui.on" : "gui.off");

            SettlingClient.instance.updateGrab();

            Settings.saveSettings();
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {}

    @Override
    protected void onMouseScrolled(int wheel) {}

}
