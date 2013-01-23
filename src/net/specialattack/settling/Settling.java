package net.specialattack.settling;

import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Settling implements Runnable {
	public static Settling instance;
	private boolean running = false;
	private boolean shuttingDown = false;
	private Canvas canvas;
	private int displayWidth;
	private int displayHeight;

	protected Settling() {
		if (instance != null) {
			throw new IllegalStateException("An instance is already running!");
		}

		instance = this;
	}

	public void attemptShutdown() {
		this.shuttingDown = true;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void run() {
		if (this.running) {
			return;
		}

		this.running = true;

		try {
			Display.setParent(this.canvas);
			Display.create();
		} catch (LWJGLException ex) {
			this.running = false;
			ex.printStackTrace();
		}

		this.displayWidth = Display.getWidth();
		this.displayHeight = Display.getHeight();

		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		while (this.running) {
			if (this.shuttingDown) {
				this.running = false;
			}

			if (this.displayWidth != this.canvas.getWidth() || this.displayHeight != this.canvas.getHeight()) {
				this.displayWidth = this.canvas.getWidth();
				this.displayHeight = this.canvas.getHeight();
			}

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, displayWidth, displayHeight, 0, 100, -100);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(0, 0, -11);
			GL11.glVertex3f(displayWidth, 0, 11);
			GL11.glVertex3f(displayWidth, displayHeight, 0);
			GL11.glVertex3f(0, displayHeight, 0);
			GL11.glEnd();

			Display.sync(60);
			Display.update();
		}

		Display.destroy();

		this.running = false;
	}
}
