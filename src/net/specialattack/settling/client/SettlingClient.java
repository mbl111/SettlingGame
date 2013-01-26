
package net.specialattack.settling.client;

import java.awt.Canvas;

import net.specialattack.settling.client.item.ClientItemDelegate;
import net.specialattack.settling.client.rendering.TileRenderer;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Item;
import net.specialattack.settling.common.item.ItemTile;
import net.specialattack.settling.common.item.Items;
import net.specialattack.settling.common.util.TickTimer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class SettlingClient extends Settling {
    private Canvas canvas;
    private int displayWidth;
    private int displayHeight;
    private TickTimer timer = new TickTimer(20.0F);
    private PlayerView player;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void resize(int width, int height) {
        this.displayWidth = width <= 0 ? 1 : width;
        this.displayHeight = height <= 0 ? 1 : height;

        GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, -1000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
        // GL11.glEnable(GL11.GL_TEXTURE_2D);

        try {
            Mouse.create();
            Mouse.setGrabbed(true);
        }

        catch (LWJGLException e) {
            e.printStackTrace();
            return false;
        }

        this.player = new PlayerView();

        return true;
    }

    @Override
    protected void shutdown() {
        Display.destroy();

        System.exit(0);
    }

    @Override
    protected void runGameLoop() {
        int frames = 0;
        int ticks = 0;
        long lastTimer1 = System.currentTimeMillis();
        while (this.isRunning() && !this.isShuttingDown()) {
            if (Display.isCloseRequested()) {
                this.attemptShutdown();
            }

            this.timer.update();
            
            for(int i = 0; i < timer.remainingTicks; i++){
                ticks++;
                this.tick();
            }

            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            frames++;
            this.render();
            Display.update();

            Display.sync(60);

            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                System.out.println(ticks + " ticks - " + frames + " fps");
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void tick() {
        this.player.tick();
    }

    private void render() {
        if (this.displayWidth != this.canvas.getWidth() || this.displayHeight != this.canvas.getHeight()) {
            this.resize(this.canvas.getWidth(), this.canvas.getHeight());
        }
        float scale = 1F;
        GL11.glScalef(scale, scale, scale);
        this.clearGL();
        this.render3D();
        this.render2D();

    }

    private void render2D() {
        this.initGL2();
        GL11.glLoadIdentity();

    }

    private void render3D() {
        this.initGL3();
        GL11.glPushMatrix();
        this.player.lookThrough();
        // This would go to the world/level class
        this.levelRender();

        GL11.glPopMatrix();

    }

    // Our 3d rendering
    public void initGL3() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GLU.gluPerspective(90.0F, this.displayWidth / this.displayHeight, 1.0F, 2000.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        //GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    // 2d rendering (For GUI and stuff)
    public void initGL2() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, this.displayWidth, this.displayHeight, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    // Clear the canvas
    private void clearGL() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glLoadIdentity();
    }

    private void levelRender() {
        //For this you would get X chunks around the player and render it based on local co-ords.
        //We need to use chunks as a help in rendering
        //Each chunk would be in a display list rather than rendering every visable block every time

        ItemTile grass = Items.grass;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                TileRenderer.renderTileFloor(grass, x, 0, z);
            }
        }

    }

}
