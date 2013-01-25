
package net.specialattack.settling.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.specialattack.settling.client.item.ClientItemDelegate;
import net.specialattack.settling.client.rendering.TileRenderer;
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

        GL11.glTranslatef(0.0F, (float) this.displayHeight / 2.0F, 0.0F);

        GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(60.0F, 1.0F, 1.0F, 0.0F);
        //GL11.glScalef(2.0F, 2.0F, 0.0F);

        ItemTile grass = Items.grass;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                TileRenderer.renderTileFloor(grass, x, 0, z);
            }
        }

        GL11.glPopMatrix();

        Display.sync(60);
        Display.update();
    }
}
