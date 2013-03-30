
package net.specialattack.settling.client.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

    public void loadImageData() {
        if (imageData == null || textureId < 0) {
            return;
        }

        int[] pixels = new int[imageData.getWidth() * imageData.getHeight()];
        imageData.getRGB(0, 0, imageData.getWidth(), imageData.getHeight(), pixels, 0, imageData.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(imageData.getWidth() * imageData.getHeight() * 4);

        for (int y = 0; y < imageData.getHeight(); y++) {
            for (int x = 0; x < imageData.getWidth(); x++) {
                int pixel = pixels[y * imageData.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, imageData.getWidth(), imageData.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

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
