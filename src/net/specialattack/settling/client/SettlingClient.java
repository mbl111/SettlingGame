
package net.specialattack.settling.client;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.File;

import net.specialattack.settling.client.item.ClientItemDelegate;
import net.specialattack.settling.client.rendering.ShaderLoader;
import net.specialattack.settling.client.rendering.TileRenderer;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.client.world.WorldDemo;
import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Item;
import net.specialattack.settling.common.item.ItemTile;
import net.specialattack.settling.common.item.Items;
import net.specialattack.settling.common.util.TickTimer;
import net.specialattack.settling.common.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;

public class SettlingClient extends Settling {

    private Canvas canvas;
    private int displayWidth;
    private int displayHeight;
    private TickTimer timer = new TickTimer(20.0F);
    private PlayerView player;
    private int shader;
    public static final boolean firstPerson = true;
    public World currentWorld;

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
            if (firstPerson) {
                Mouse.setGrabbed(true);
            }
        }

        catch (LWJGLException e) {
            e.printStackTrace();
            return false;
        }

        this.player = new PlayerView();

        this.shader = ShaderLoader.createProgram("sepia");

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL20.glUseProgram(this.shader);

        TextureRegistry.tiles.bindTexture();

        int loc = GL20.glGetUniformLocation(this.shader, "texture1");
        GL20.glUniform1i(loc, 0);

        GL20.glUseProgram(0);

        BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);

        this.currentWorld = new WorldDemo(new File("./demo/"));

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int value = this.currentWorld.getChunkAtTile(x - 64, y - 64).getHeight(x % 16, y % 16);

                int color = value - 128;

                img.setRGB(x, y, color | color << 8 | color << 16);
            }
        }

        TextureRegistry.items.loadTexture(img, "test", img.getWidth(), img.getHeight());

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

            for (int i = 0; i < this.timer.remainingTicks; i++) {
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
        if (firstPerson) {
            this.player.tick();
        }
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

        if (firstPerson) {
            this.player.lookThrough(this.timer.renderPartialTicks);
        }
        else {
            GL11.glTranslatef(0.0F, (float) this.displayHeight / 2.0F, 0.0F);

            GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(60.0F, 1.0F, 1.0F, 0.0F);
            // GL11.glScalef(25.0F, 25.0F, 25.0F);
        }
        // This would go to the world/level class

        this.levelRender();

        GL11.glPopMatrix();

    }

    // Our 3d rendering
    public void initGL3() {
        if (firstPerson) {
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();

            GLU.gluPerspective(90.0F, (float) this.displayWidth / (float) this.displayHeight, 0.5F, 2000.0F);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }
        else {
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, -1000.0D);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.5F);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        // GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    // 2d rendering (For GUI and stuff)
    public void initGL2() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D, -1.0D, 1.0D);
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
        // For this you would get X chunks around the player and render it based
        // on local co-ords.
        // We need to use chunks as a help in rendering
        // Each chunk would be in a display list rather than rendering every
        // visable block every time
        // Muahahahahaha, no spell checking!

        // GL20.glUseProgram(this.shader);

        TileRenderer.resetTexture();

        //GL20.glUseProgram(this.shader);

        TileRenderer.resetTexture();

        ItemTile grass = Items.grass;
        // TileRenderer.renderTileFloor(grass, 0, 0, 0);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                TileRenderer.renderTileFloor(grass, x, 0, z);
            }
        }

        for (int x = this.currentWorld.getMinXBorder(); x < this.currentWorld.getMaxXBorder(); x++) {
            for (int z = this.currentWorld.getMinZBorder(); z < this.currentWorld.getMaxZBorder(); z++) {
                TileRenderer.renderTileFloor(grass, x, this.currentWorld.getChunkAtTile(x, z).getHeight(x % 16, z % 16), z);
            }
        }

        // GL20.glUseProgram(0);

    }

}
