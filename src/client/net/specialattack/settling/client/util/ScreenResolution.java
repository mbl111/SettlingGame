
package net.specialattack.settling.client.util;

import java.util.TreeSet;
import java.util.logging.Level;

import net.specialattack.settling.common.Settling;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class ScreenResolution {

    private static TreeSet<DisplayMode> modes;

    public static void initialize() {
        try {
            DisplayMode[] displayModes = Display.getAvailableDisplayModes();

            modes = new TreeSet<DisplayMode>(new DisplayModeComparator());

            for (DisplayMode mode : displayModes) {
                if (mode.isFullscreenCapable() && mode.getBitsPerPixel() == 32) {
                    modes.add(mode);
                }
            }
        }
        catch (LWJGLException e) {
            Settling.log.log(Level.SEVERE, "Failed loading fullscreen capabilities", e);
        }

    }

    public static DisplayMode[] getDisplayModes() {
        return modes.toArray(new DisplayMode[0]);
    }

    public static DisplayMode getDisplayMode(String mode) {
        String[] modeInfo = mode.split("x", 4);

        if (modeInfo.length != 4) {
            return null;
        }

        int width = Integer.parseInt(modeInfo[0]);
        int height = Integer.parseInt(modeInfo[1]);
        int frequency = Integer.parseInt(modeInfo[2]);
        int bitsPerPixel = Integer.parseInt(modeInfo[3]);

        for (DisplayMode displayMode : modes) {
            if (width != displayMode.getWidth()) {
                continue;
            }
            if (height != displayMode.getHeight()) {
                continue;
            }
            if (frequency != displayMode.getFrequency()) {
                continue;
            }
            if (bitsPerPixel != displayMode.getBitsPerPixel()) {
                continue;
            }
            return displayMode;
        }

        return null;
    }

    public static String getDisplayMode(DisplayMode mode) {
        return mode.getWidth() + "x" + mode.getHeight() + "x" + mode.getFrequency() + "x" + mode.getBitsPerPixel();
    }

    public static String getReadableDisplayMode(DisplayMode mode) {
        return mode.getWidth() + " x " + mode.getHeight() + " x " + mode.getFrequency() + " @" + mode.getBitsPerPixel();
    }

}
