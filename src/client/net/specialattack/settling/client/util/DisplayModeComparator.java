
package net.specialattack.settling.client.util;

import java.util.Comparator;

import org.lwjgl.opengl.DisplayMode;

public class DisplayModeComparator implements Comparator<DisplayMode> {

    @Override
    public int compare(DisplayMode mode1, DisplayMode mode2) {
        if (mode1.getHeight() > mode2.getHeight()) {
            return 1;
        }
        else if (mode1.getHeight() == mode2.getHeight()) {
            if (mode1.getWidth() > mode2.getWidth()) {
                return 1;
            }
            else if (mode1.getWidth() == mode2.getWidth()) {
                if (mode1.getFrequency() > mode2.getFrequency()) {
                    return 1;
                }
                else if (mode1.getFrequency() == mode2.getFrequency()) {
                    if (mode1.getBitsPerPixel() > mode2.getBitsPerPixel()) {
                        return 1;
                    }
                    else if (mode1.getBitsPerPixel() == mode2.getBitsPerPixel()) {
                        return 0;
                    }
                    else {
                        return -1;
                    }
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }

}
