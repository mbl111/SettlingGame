
package net.specialattack.settling.common.texture;

import java.awt.image.BufferedImage;

public interface IStitchedTexture {
    public abstract ISubTexture loadTexture(BufferedImage imageData, String name, int width, int height);

    public abstract ISubTexture getSubTexture(String name);
}
