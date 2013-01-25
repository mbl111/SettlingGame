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
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.glu.GLU;

public class SettlingClient extends Settling {
	private Canvas canvas;
	private int displayWidth;
	private int displayHeight;
	private int fps;
	private PlayerView player;

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
				} catch (ClassCastException ex) {
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
		} catch (LWJGLException ex) {
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
		// GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D,
		// 1000.0D, -1000.0D);
		// GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// GL11.glEnable(GL11.GL_TEXTURE_2D);

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
		} catch (OpenGLException ex) {
			ex.printStackTrace();

			text = new SubTexture(texture, image, 0, 0, 16, 16);
		}

		text.bindTexture();

		try {
			Mouse.create();
			Mouse.setGrabbed(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			return false;
		}

		player = new PlayerView();

		return true;
	}

	@Override
	protected void shutdown() {
		Display.destroy();

		System.exit(0);
	}

	@Override
	protected void runGameLoop() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		while (isRunning() && !isShuttingDown()) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;

			if (Display.isCloseRequested()) {
				shutdown();
			}

			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			{
				if (shouldRender) {
					frames++;
					render();
					Display.update();
				}
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				this.fps = frames;
				System.out.println(ticks + " ticks - " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
		Display.sync(60);
	}

	private void tick() {
		player.tick();
	}

	private void render() {
		// if (this.displayWidth != this.canvas.getWidth() || this.displayHeight
		// != this.canvas.getHeight()) {
		// this.resize(this.canvas.getWidth(), this.canvas.getHeight());
		//
		// GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		//
		// GL11.glMatrixMode(GL11.GL_PROJECTION);
		// GL11.glLoadIdentity();
		// GL11.glOrtho(0.0D, this.displayWidth, this.displayHeight, 0.0D,
		// 1000.0D, -1000.0D);
		// GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// }
		float scale = 1F;
		GL11.glScalef(scale, scale, scale);
		clearGL();
		render3D();
		render2D();

	}

	private void render2D() {
		initGL2();
		GL11.glLoadIdentity();

		
		
	}

	private void render3D() {
		initGL3();
		GL11.glPushMatrix();
		player.lookThrough();
		// This would go to the world/level class
		levelRender();

		GL11.glPopMatrix();

	}

	// Our 3d rendering
	public void initGL3() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective((float) 90, Display.getWidth() / Display.getHeight(), 0.001f, 2000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	// 2d rendering (For GUI and stuff)
	public void initGL2() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
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
		SubTexture text = TextureRegistry.getSubTexture("grass");
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				GL11.glBegin(GL11.GL_QUADS);

				float startU = 1.0F * (float) text.getStartU() / (float) text.getParent().getWidth();
				float startV = 1.0F * (float) text.getStartV() / (float) text.getParent().getHeight();
				float endU = 1.0F * (float) text.getEndU() / (float) text.getParent().getWidth();
				float endV = 1.0F * (float) text.getEndV() / (float) text.getParent().getHeight();

				GL11.glTexCoord2f(startU, startV);
				GL11.glVertex3f(x * 50.0F, 0.0F, z * 50.0F);

				GL11.glTexCoord2f(endU, startV);
				GL11.glVertex3f((x + 1) * 50.0F, 0.0F, z * 50.0F);

				GL11.glTexCoord2f(endU, endV);
				GL11.glVertex3f((x + 1) * 50.0F, 0.0F, (z + 1) * 50.0F);

				GL11.glTexCoord2f(startU, endV);
				GL11.glVertex3f(x * 50.0F, 0.0F, (z + 1) * 50.0F);
				GL11.glEnd();
			}
		}

	}

}
