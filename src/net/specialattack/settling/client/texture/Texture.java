
package net.specialattack.settling.client.texture;

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

    public int bindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);

        return this.textureId;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTextureId() {
        return this.textureId;
    }

    public float[] getPixelLocations(int indexU, int indexV) {
        float u = (float) indexU / (float) this.width;
        float v = (float) indexV / (float) this.height;

        return new float[] { u, v };
    }
}
