
package net.specialattack.settling.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class StitchedTexture extends Texture {

    private HashMap<String, SubTexture> textures;
    private static HashMap<String, SubTexture> globalTextures = new HashMap<String, SubTexture>();
    private int availableSlots;
    private boolean[] slots;
    private int slotsU;
    private int slotsV;

    public StitchedTexture(int textureId, BufferedImage imageData, int width, int height) {
        super(textureId, imageData, width, height);

        this.width -= this.width & 0xf;
        this.height -= this.height & 0xf;
        this.slotsU = this.width >> 4;
        this.slotsV = this.height >> 4;

        this.availableSlots = this.slotsU * this.slotsV;

        this.textures = new HashMap<String, SubTexture>();

        this.slots = new boolean[this.availableSlots];

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

    public SubTexture loadTexture(BufferedImage imageData, String name, int width, int height) {
        if (availableSlots <= 0) {
            throw new RuntimeException("No more texture slots available");
        }

        if (!this.textures.containsKey(name)) {
            width -= width & 0xf;
            height -= height & 0xf;

            int startSlot = findNextSizedSlotAndPopulate(width >> 4, height >> 4);

            if (startSlot < 0) {
                throw new RuntimeException("No more texture slots available");
            }

            SubTexture texture = new SubTexture(this, imageData, startSlot % slotsU, startSlot / slotsU, startSlot % slotsU + width, startSlot / slotsU + height);

            registerTexture(name, texture);

            availableSlots -= (width >> 4) * (height >> 4);

            BufferedImage image = texture.imageData;

            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            buffer.flip();

            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, texture.startU, texture.startV, texture.endU - texture.startU, texture.endV - texture.startV, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            return texture;
        }

        return this.textures.get(name);
    }

    private int findNextSizedSlotAndPopulate(int width, int height) {
        for (int u1 = 0; u1 < slotsU - width; u1++) {
            for (int v1 = 0; v1 < slotsV - height; v1++) {
                boolean empty = true;

                for (int u2 = u1; u2 < u1 + width; u2++) {
                    for (int v2 = v1; v2 < v1 + height; v2++) {
                        if (slots[u2 + v2 * slotsU] == true) {
                            empty = false;
                        }
                    }
                }

                if (empty) {
                    for (int u2 = u1; u2 < u1 + width; u2++) {
                        for (int v2 = v1; v2 < v1 + height; v2++) {
                            slots[u2 + v2 * slotsU] = true;
                        }
                    }

                    return u1 + v1 * slotsU;
                }
            }
        }

        return -1;
    }

    public SubTexture getSubTexture(String name) {
        if (this.textures.containsKey(name)) {
            return this.textures.get(name);
        }

        return null;
    }

    public static SubTexture getTexture(String name) {
        if (globalTextures.containsKey(name)) {
            return globalTextures.get(name);
        }

        return null;
    }

    public static void registerTexture(String name, SubTexture texture) {
        if (!globalTextures.containsKey(name)) {
            globalTextures.put(name, texture);
        }
    }

}
