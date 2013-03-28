
package net.specialattack.settling.client.util;

import org.lwjgl.opengl.DisplayMode;

public class DisplayModeSetting implements ISetting {

    private DisplayMode mode;

    public DisplayModeSetting() {
        this.mode = ScreenResolution.getDisplayModes()[0];
        Settings.settings.put("display.mode", this);
    }

    @Override
    public String getKey() {
        return "display.mode";
    }

    @Override
    public String getValue() {
        return ScreenResolution.getDisplayMode(mode);
    }

    @Override
    public void loadValue(String obj) {
        this.mode = ScreenResolution.getDisplayMode(obj);
    }

    @Override
    public void update() {}

    public void setMode(DisplayMode mode) {
        this.mode = mode;
    }

    public DisplayMode getMode() {
        return this.mode;
    }

}
