
package net.specialattack.settling.client.gui;

import org.lwjgl.opengl.GL11;

public class GuiHelper {

    public static void renderRectangle(int posX, int posY, int width, int height, int color) {
        GL11.glBegin(GL11.GL_QUADS);

        int red = (color >> 24) & 0xFF;
        int green = (color >> 16) & 0xFF;
        int blue = (color >> 8) & 0xFF;
        int alpha = color & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX, posY);
        GL11.glVertex2i(posX + width, posY);
        GL11.glVertex2i(posX + width, posY + height);
        GL11.glVertex2i(posX, posY + height);

        GL11.glEnd();
    }

    public static void renderRectangle(int posX, int posY, int width, int height, int color1, int color2) {
        GL11.glBegin(GL11.GL_QUADS);

        int red = (color1 >> 24) & 0xFF;
        int green = (color1 >> 16) & 0xFF;
        int blue = (color1 >> 8) & 0xFF;
        int alpha = color1 & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX, posY);
        GL11.glVertex2i(posX + width, posY);

        red = (color2 >> 24) & 0xFF;
        green = (color2 >> 16) & 0xFF;
        blue = (color2 >> 8) & 0xFF;
        alpha = color2 & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX + width, posY + height);
        GL11.glVertex2i(posX, posY + height);

        GL11.glEnd();
    }

    public static void renderRectangle(int posX, int posY, int width, int height, int color1, int color2, int color3, int color4) {
        GL11.glBegin(GL11.GL_QUADS);

        int red = (color1 >> 24) & 0xFF;
        int green = (color1 >> 16) & 0xFF;
        int blue = (color1 >> 8) & 0xFF;
        int alpha = color1 & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX, posY);

        red = (color2 >> 24) & 0xFF;
        green = (color2 >> 16) & 0xFF;
        blue = (color2 >> 8) & 0xFF;
        alpha = color2 & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX + width, posY);

        red = (color3 >> 24) & 0xFF;
        green = (color3 >> 16) & 0xFF;
        blue = (color3 >> 8) & 0xFF;
        alpha = color3 & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX + width, posY + height);

        red = (color4 >> 24) & 0xFF;
        green = (color4 >> 16) & 0xFF;
        blue = (color4 >> 8) & 0xFF;
        alpha = color4 & 0xFF;

        GL11.glColor4f((float) red / 256.0F, (float) green / 256.0F, (float) blue / 256.0F, (float) alpha / 256.0F);
        GL11.glVertex2i(posX, posY + height);

        GL11.glEnd();
    }

    public static void drawTexturedRectangle(int posX, int posY, int width, int height, float startU, float startV, float endU, float endV) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2d(startU, startV);
        GL11.glVertex2i(posX, posY);

        GL11.glTexCoord2d(endU, startV);
        GL11.glVertex2i(posX + width, posY);

        GL11.glTexCoord2d(endU, endV);
        GL11.glVertex2i(posX + width, posY + height);

        GL11.glTexCoord2d(startU, endV);
        GL11.glVertex2i(posX, posY + height);

        GL11.glEnd();
    }

    public static void drawTexturedRectangle(float posX, float posY, float width, float height, float startU, float startV, float endU, float endV) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2d(startU, startV);
        GL11.glVertex2f(posX, posY);

        GL11.glTexCoord2d(endU, startV);
        GL11.glVertex2f(posX + width, posY);

        GL11.glTexCoord2d(endU, endV);
        GL11.glVertex2f(posX + width, posY + height);

        GL11.glTexCoord2d(startU, endV);
        GL11.glVertex2f(posX, posY + height);

        GL11.glEnd();
    }

    public static void drawTexturedRectangle2(int posX, int posY, int width, int height, float startU, float startV, float endU, float endV) {
        GL11.glTexCoord2d(startU, startV);
        GL11.glVertex2i(posX, posY);

        GL11.glTexCoord2d(endU, startV);
        GL11.glVertex2i(posX + width, posY);

        GL11.glTexCoord2d(endU, endV);
        GL11.glVertex2i(posX + width, posY + height);

        GL11.glTexCoord2d(startU, endV);
        GL11.glVertex2i(posX, posY + height);
    }

    public static void drawTexturedRectangle2(float posX, float posY, float width, float height, float startU, float startV, float endU, float endV) {
        GL11.glTexCoord2d(startU, startV);
        GL11.glVertex2f(posX, posY);

        GL11.glTexCoord2d(endU, startV);
        GL11.glVertex2f(posX + width, posY);

        GL11.glTexCoord2d(endU, endV);
        GL11.glVertex2f(posX + width, posY + height);

        GL11.glTexCoord2d(startU, endV);
        GL11.glVertex2f(posX, posY + height);
    }
}
