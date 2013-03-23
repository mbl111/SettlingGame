
package net.specialattack.settling.client.rendering;

import net.specialattack.settling.client.texture.Texture;
import net.specialattack.settling.client.texture.TextureRegistry;

import org.lwjgl.opengl.GL11;

public class FontRenderer {

    private Texture texture;
    private static final int[] widths = new int[] { //
    15, 15, 14, 14, 15, 15, 15, 15, 15, 14, 14, 14, 14, 14, 15, 14, // 0
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // 1
    8, 8, 6, 15, 15, 15, 15, 16, 8, 8, 16, 16, 4, 10, 4, 16, // 2
    10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 4, 16, 16, 16, 16, 10, // 3
    16, 14, 14, 14, 14, 14, 12, 14, 14, 12, 12, 14, 14, 14, 14, 14, // 4
    12, 14, 12, 14, 12, 14, 14, 14, 14, 14, 14, 6, 16, 6, 16, 16, // 5
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // 6
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // 7
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // 8
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // 9
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // A
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // B
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // C
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // D
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // E
    16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, // F
    };

    public float fontSize = 16.0F;

    public FontRenderer() {
        this.texture = TextureRegistry.getTexture("/font.png");
    }

    public void renderStringWithShadow(String text, int posX, int posY, int color) {
        int red = (color >> 24) & 0xFF / 2;
        int green = (color >> 16) & 0xFF / 2;
        int blue = (color >> 8) & 0xFF / 2;
        int alpha = color & 0xFF;

        renderString(text, posX + 2, posY + 2, (red << 24) | (green << 16) | (blue << 8) | alpha);
        renderString(text, posX, posY, color);
    }

    public void renderString(String text, int posX, int posY, int color) {
        char[] chars = text.toCharArray();

        int nextPosX = posX;

        int red = (color >> 24) & 0xFF;
        int green = (color >> 16) & 0xFF;
        int blue = (color >> 8) & 0xFF;
        int alpha = color & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);

        for (int i = 0; i < chars.length; i++) {
            nextPosX = renderChar(chars[i], nextPosX, posY);
        }
    }

    public int renderChar(char character, int posX, int posY) {
        this.texture.bindTexture();

        int charIndex = character & 0xFF;

        int u = charIndex & 0xF;
        int v = (charIndex - u) / 16;

        float[] start = this.texture.getPixelLocations(u * 16, v * 16);
        float[] end = this.texture.getPixelLocations(u * 16 + widths[charIndex], v * 16 + 16);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(start[0], start[1]);
        GL11.glVertex3f(posX, posY, 0.0F);

        GL11.glTexCoord2f(end[0], start[1]);
        GL11.glVertex3f(posX + (float) widths[charIndex] * fontSize / 16.0F, posY, 0.0F);

        GL11.glTexCoord2f(end[0], end[1]);
        GL11.glVertex3f(posX + (float) widths[charIndex] * fontSize / 16.0F, posY + fontSize, 0.0F);

        GL11.glTexCoord2f(start[0], end[1]);
        GL11.glVertex3f(posX, posY + fontSize, 0.0F);

        GL11.glEnd();

        return posX + (int) ((float) widths[charIndex] * fontSize / 16.0F) + 2;
    }

    public int getStringWidth(String text) {
        char[] chars = text.toCharArray();

        int width = 0;

        for (int i = 0; i < chars.length; i++) {
            width += getCharWidth(chars[i]) + 2;
        }

        return width;
    }

    public int getCharWidth(char character) {
        int charIndex = character & 0xFF;

        return (int) ((float) widths[charIndex] * fontSize / 16.0F);
    }

}
