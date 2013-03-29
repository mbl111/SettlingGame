
package net.specialattack.settling.client.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;

import net.specialattack.settling.common.texture.IStitchedTexture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class StitchedTexture extends Texture implements IStitchedTexture {

    private HashMap<String, SubTexture> textures;
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

    @Override
    public SubTexture loadTexture(BufferedImage imageData, String name, int width, int height) {
        if (this.availableSlots <= 0) {
            throw new RuntimeException("No more texture slots available");
        }

        if (!this.textures.containsKey(name)) {
            width -= width & 0xf;
            height -= height & 0xf;

            int startSlot = this.findNextSizedSlotAndPopulate(width >> 4, height >> 4);

            if (startSlot < 0) {
                throw new RuntimeException("No more texture slots available");
            }

            SubTexture texture = new SubTexture(this, imageData, (startSlot % this.slotsU) * 16, (startSlot / this.slotsU) * 16, (startSlot % this.slotsU) * 16 + width, (startSlot / this.slotsU) * 16 + height);

            registerTexture(name, texture);

            this.availableSlots -= (width >> 4) * (height >> 4);

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

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);

            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, texture.startU, texture.startV, texture.endU - texture.startU, texture.endV - texture.startV, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            this.textures.put(name, texture);

            return texture;
        }

        return this.textures.get(name);
    }

    private int findNextSizedSlotAndPopulate(int width, int height) {
        for (int u1 = 0; u1 < this.slotsU - width + 1; u1++) {
            for (int v1 = 0; v1 < this.slotsV - height + 1; v1++) {
                boolean empty = true;

                for (int u2 = u1; u2 < u1 + width; u2++) {
                    for (int v2 = v1; v2 < v1 + height; v2++) {
                        if (this.slots[u2 + v2 * this.slotsU] == true) {
                            empty = false;
                        }
                    }
                }

                if (empty) {
                    for (int u2 = u1; u2 < u1 + width; u2++) {
                        for (int v2 = v1; v2 < v1 + height; v2++) {
                            this.slots[u2 + v2 * this.slotsU] = true;
                        }
                    }

                    return u1 + v1 * this.slotsU;
                }
            }
        }

        return -1;
    }

    @Override
    public SubTexture getSubTexture(String name) {
        if (this.textures.containsKey(name)) {
            return this.textures.get(name);
        }

        return TextureRegistry.textureNotFound;
    }

    @Override
    public int getTextureId() {
        return this.textureId;
    }

    public static void registerTexture(String name, SubTexture texture) {
        if (!TextureRegistry.subTextures.containsKey(name)) {
            TextureRegistry.subTextures.put(name, texture);
        }
    }

    @Override
    public float getPadding() {
        // TODO: return 0.5F / (float) this.getWidth();
        return 0.0F;
    }
}
