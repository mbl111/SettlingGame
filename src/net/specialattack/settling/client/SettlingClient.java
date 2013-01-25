
package net.specialattack.settling.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.specialattack.settling.client.item.ClientItemDelegate;
import net.specialattack.settling.client.texture.StitchedTexture;
import net.specialattack.settling.client.texture.SubTexture;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Item;
import net.specialattack.settling.common.item.ItemTile;
import net.specialattack.settling.common.item.Items;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.OpenGLException;

public class SettlingClient extends Settling {
    private Canvas canvas;
    private int displayWidth;
    private int displayHeight;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void resize(int width, int height) {
        this.displayWidth = width <= 0 ? 1 : width;
        this.displayHeight = height <= 0 ? 1 : height;
    }

    @Override
    public CommonItemDelegate getItemDelegate() {
        return new ClientItemDelegate();
    }

    @Override
    public void finishItems() {
        for (Item item : Items.itemList) {
            if (item != null) {
                try {
                    ((ItemTile) item).delegate.registerTextures(TextureRegistry.tiles);
                }
                catch (ClassCastException ex) {
                    item.delegate.registerTextures(TextureRegistry.tiles);
                }
            }
        }
    }

    @Override
    protected boolean startup() {
        try {
            Display.setParent(this.canvas);
            Display.create();
        }
        catch (LWJGLException ex) {
            ex.printStackTrace();
            return false;
        }

        this.displayWidth = Display.getWidth();
        this.displayHeight = Display.getHeight();

        // TODO: Move this stuff over

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, -1000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        BufferedImage base = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = base.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 256, 256);
        graphics.dispose();

        StitchedTexture texture = new StitchedTexture(GL11.glGenTextures(), base, 256, 256);

        texture.bindTexture();

        BufferedImage image = TextureRegistry.openResource("/grass.png");

        SubTexture text = null;
        try {
            text = texture.loadTexture(image, "grass", image.getWidth(), image.getHeight());
        }
        catch (OpenGLException ex) {
            ex.printStackTrace();

            text = new SubTexture(texture, image, 0, 0, 16, 16);
        }

        text.bindTexture();

        return true;
    }

    @Override
    protected void shutdown() {
        Display.destroy();

        System.exit(0);
    }

    @Override
    protected void runGameLoop() {

        if (this.displayWidth != this.canvas.getWidth() || this.displayHeight != this.canvas.getHeight()) {
            this.resize(this.canvas.getWidth(), this.canvas.getHeight());

            GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, -1000.0D);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }

        GL11.glPushMatrix();

        //GL11.glTranslatef((float) this.displayWidth / 2.0F, (float) this.displayHeight / 2.0F, 0.0F);
        //GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
        //GL11.glRotatef(45.0F, 1.0F, 1.0F, 0.0F);
        GL11.glScalef(5.0F, 5.0F, 0.0F);

        SubTexture text = TextureRegistry.getSubTexture("grass");

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                GL11.glPushMatrix();

                GL11.glTranslatef((float) x * 25.0F, (float) z * 25.0F + (x % 2) * 12.5F, 0.0F);

                GL11.glBegin(GL11.GL_QUADS);

                float startU = 1.0F * (float) text.getStartU() / (float) text.getParent().getWidth();
                float startV = 1.0F * (float) text.getStartV() / (float) text.getParent().getHeight();
                float endU = 1.0F * (float) text.getEndU() / (float) text.getParent().getWidth();
                float endV = 1.0F * (float) text.getEndV() / (float) text.getParent().getHeight();

                GL11.glTexCoord2f(startU, startV);
                GL11.glVertex3f(25.0F, 0.0F, 0.0F);

                GL11.glTexCoord2f(endU, startV);
                GL11.glVertex3f(50.0F, 12.5F, 0.0F);

                GL11.glTexCoord2f(endU, endV);
                GL11.glVertex3f(25.5F, 25.2F, 0.0F); // 25.2F to prevent borders

                GL11.glTexCoord2f(startU, endV);
                GL11.glVertex3f(0.0F, 12.5F, 0.0F);

                GL11.glEnd();

                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();

        Display.sync(60);
        Display.update();
    }
}
