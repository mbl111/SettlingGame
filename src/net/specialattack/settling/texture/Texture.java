
package net.specialattack.settling.texture;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class Texture {
    protected int textureId;
    protected int width;
    protected int height;
    protected BufferedImage imageData;

    public Texture(int textureId, BufferedImage imageData, int width, int height) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.imageData = imageData;
    }

    public void bindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
