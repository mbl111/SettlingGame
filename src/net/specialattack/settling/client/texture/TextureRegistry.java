
package net.specialattack.settling.client.texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TextureRegistry {

    private static HashMap<String, Texture> loadedTextures = new HashMap<String, Texture>();
    private static HashMap<String, StitchedTexture> stitchedTextures = new HashMap<String, StitchedTexture>();
    protected static HashMap<String, SubTexture> subTextures = new HashMap<String, SubTexture>();

    protected static SubTexture textureNotFound;
    public static StitchedTexture items;
    public static StitchedTexture tiles;
    private static BufferedImage missingTexture;

    static {
        BufferedImage base = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = base.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 256, 256);
        graphics.dispose();

        items = new StitchedTexture(GL11.glGenTextures(), base, 256, 256);
        tiles = new StitchedTexture(GL11.glGenTextures(), base, 256, 256);

        missingTexture = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);

        graphics = missingTexture.getGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 128, 128);
        graphics.setColor(Color.black);
        graphics.drawString("missingno", 32, 54);
        graphics.dispose();

        textureNotFound = new ErrorSubTexture(missingTexture, GL11.glGenTextures());
    }

    public static BufferedImage openResource(String path) {
        URL url = TextureRegistry.class.getResource(path);
        BufferedImage image = null;

        try {
            image = ImageIO.read(url);
        }
        catch (IOException e) {
            e.printStackTrace();

            image = missingTexture;
        }

        return image;
    }

    public static Texture getTexture(String name) {
        if (loadedTextures.containsKey(name)) {
            return loadedTextures.get(name);
        }
        else {
            URL url = TextureRegistry.class.getResource(name);
            BufferedImage image = null;

            try {
                image = ImageIO.read(url);
            }
            catch (IOException e) {
                e.printStackTrace();

                image = missingTexture;
            }

            int id = GL11.glGenTextures(); // Generate texture Id if it doesn't exist!
            loadTexture(image, id);

            Texture texture = new Texture(id, image, image.getWidth(), image.getHeight());

            loadedTextures.put(name, texture);

            return texture;
        }
    }

    public static StitchedTexture getStitchedTexture(String name) {
        if (stitchedTextures.containsKey(name)) {
            return stitchedTextures.get(name);
        }

        return null;
    }

    public static SubTexture getSubTexture(String name) {
        if (subTextures.containsKey(name)) {
            return subTextures.get(name);
        }

        return TextureRegistry.textureNotFound;
    }

    public static void addStitchedTexture(String name, StitchedTexture texture) {
        stitchedTextures.put(name, texture);
    }

    public static void loadTexture(BufferedImage image, int id) {
        if (image == null || id < 0) {
            return;
        }

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

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }

}
