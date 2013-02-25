
package net.specialattack.settling.client;

import java.awt.Canvas;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.specialattack.settling.client.item.ClientItemDelegate;
import net.specialattack.settling.client.rendering.ChunkRenderer;
import net.specialattack.settling.client.rendering.FontRenderer;
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
import net.specialattack.settling.common.world.Chunk;
import net.specialattack.settling.common.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
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
    public FontRenderer fontRenderer;
    private HashMap<Chunk, ChunkRenderer> chunkList;
    private ArrayList<Chunk> dirtyChunks;
    private ArrayList<ChunkRenderer> renderedChunks;
    private int fps;

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

        this.shader = ShaderLoader.createProgram("grayscale");

        GL13.glActiveTexture(GL13.GL_TEXTURE0);

        if (shader > 0) {
            GL20.glUseProgram(this.shader);

            TextureRegistry.tiles.bindTexture();

            int loc = GL20.glGetUniformLocation(this.shader, "texture1");
            GL20.glUniform1i(loc, 0);
        }

        this.currentWorld = new WorldDemo(new File("./demo/"));
        this.fontRenderer = new FontRenderer();
        this.chunkList = new HashMap<Chunk, ChunkRenderer>();
        this.dirtyChunks = new ArrayList<Chunk>();
        this.renderedChunks = new ArrayList<ChunkRenderer>();

        for (int x = this.currentWorld.getMinXBorder() / 16; x < this.currentWorld.getMaxXBorder() / 16; x++) {
            for (int z = this.currentWorld.getMinZBorder() / 16; z < this.currentWorld.getMaxZBorder() / 16; z++) {
                Chunk chunk = this.currentWorld.getChunkAt(x, z, false);

                if (!this.dirtyChunks.contains(chunk)) {
                    this.dirtyChunks.add(chunk);
                }
            }
        }

        this.player = new PlayerView(this.currentWorld);

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
                updateChunks();
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

            //Display.sync(60);

            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                System.out.println(ticks + " ticks - " + frames + " fps");
                this.fps = frames;
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void tick() {
        if (firstPerson) {
            this.player.tick();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {

            int xb = (int) (this.player.location.x / 50) * -1;
            int zb = (int) (this.player.location.z / 50) * -1;

            int xc = xb / 16;
            int zc = zb / 16;

            int minChunkXBorder = this.currentWorld.getMinXBorder() / 16;
            int minChunkZBorder = this.currentWorld.getMinZBorder() / 16;
            int maxChunkXBorder = this.currentWorld.getMaxXBorder() / 16;
            int maxChunkZBorder = this.currentWorld.getMaxZBorder() / 16;

            int renderChunkRadius = 10;

            for (int x = minChunkXBorder < renderChunkRadius - xc ? minChunkXBorder : renderChunkRadius - xc; x < (maxChunkXBorder < renderChunkRadius + xc ? maxChunkXBorder : renderChunkRadius + xc); x++) {
                for (int z = minChunkZBorder < renderChunkRadius - zc ? minChunkZBorder : renderChunkRadius - zc; z < (maxChunkZBorder < renderChunkRadius + zc ? maxChunkZBorder : renderChunkRadius + zc); z++) {
                    //this.currentWorld.getChunkAtTile(x * 16, z * 16).tileUpdate();
                }
            }
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

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        this.fontRenderer.renderStringWithShadow("Location: (" + this.player.location.x + ", " + this.player.location.y + ", " + this.player.location.z + ")", 0, 16, 0xFFFFFFFF);
        this.fontRenderer.renderStringWithShadow("Pitch: " + this.player.location.pitch, 0, 34, 0x00FFFFFF);
        this.fontRenderer.renderStringWithShadow("Yaw: " + this.player.location.yaw, 0, 52, 0xFF00FFFF);
        this.fontRenderer.renderStringWithShadow("Dirty chunks: " + this.dirtyChunks.size(), 0, 70, 0xFFFF00FF);

        int renderedChunks = 0;
        for (ChunkRenderer chunkRenderer : this.renderedChunks) {
            if (!chunkRenderer.dirty) {
                renderedChunks++;
            }
        }

        this.fontRenderer.renderStringWithShadow("Rendered chunks: " + renderedChunks, 0, 88, 0x888888FF);

        this.fontRenderer.renderStringWithShadow("FPS: " + this.fps, 0, 106, 0x888888FF);

        GL11.glDisable(GL11.GL_BLEND);
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

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

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
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
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

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void updateChunks() {
        if (this.dirtyChunks.size() > 0) {
            float distance = -1.0F;
            Chunk toRender = null;

            for (Chunk chunk : this.dirtyChunks) {
                float cDistance = (-this.player.location.x / 16.0F + 0.5F - (float) chunk.chunkX) * (-this.player.location.x / 16.0F + 0.5F - (float) chunk.chunkX);
                cDistance += (-this.player.location.z / 16.0F + 0.5F - (float) chunk.chunkZ) * (-this.player.location.z / 16.0F + 0.5F - (float) chunk.chunkZ);

                if (cDistance < distance || distance < 0) {
                    distance = cDistance;
                    toRender = chunk;
                }
            }

            this.dirtyChunks.remove(toRender);

            if (toRender != null) {
                ChunkRenderer chunkRenderer;
                boolean isRenderedAlready = false;

                if (chunkList.containsKey(toRender)) {
                    chunkRenderer = chunkList.get(toRender);
                    isRenderedAlready = true;
                }
                else {
                    chunkRenderer = new ChunkRenderer(toRender);
                }

                chunkRenderer.createGlChunk();

                if (!isRenderedAlready) {
                    this.chunkList.put(toRender, chunkRenderer);
                    this.renderedChunks.add(chunkRenderer);
                }
            }
        }
    }

    private void levelRender() {
        // For this you would get X chunks around the player and render it based
        // on local co-ords.
        // We need to use chunks as a help in rendering
        // Each chunk would be in a display list rather than rendering every
        // visable block every time
        // Muahahahahaha, no spell checking!

        TileRenderer.resetTexture();

        GL11.glPushMatrix();

        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        fontRenderer.renderString("Mbl111 and", -32, -64, 0xFFFFFFFF);
        fontRenderer.renderString("Heldplayer", -32, -48, 0xFFFFFFFF);
        fontRenderer.renderString("Present...", -32, -32, 0xFFFFFFFF);

        GL11.glPopMatrix();

        //GL20.glUseProgram(this.shader);

        int renderChunkRadius = 16;

        for (ChunkRenderer chunkRenderer : this.renderedChunks) {
            float distance = (-this.player.location.x / 16.0F + 0.5F - (float) chunkRenderer.chunk.chunkX) * (-this.player.location.x / 16.0F + 0.5F - (float) chunkRenderer.chunk.chunkX);
            distance += (-this.player.location.z / 16.0F + 0.5F - (float) chunkRenderer.chunk.chunkZ) * (-this.player.location.z / 16.0F + 0.5F - (float) chunkRenderer.chunk.chunkZ);

            if (distance < renderChunkRadius * renderChunkRadius) {
                chunkRenderer.renderChunk();
            }
        }

        //GL20.glUseProgram(0);

    }

}
