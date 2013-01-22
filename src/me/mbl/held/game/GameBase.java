package me.mbl.held.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

public abstract class GameBase extends Canvas implements Runnable {

	public int WIDTH = 120;
	public int HEIGHT = (int) (WIDTH * 3 / 4);
	public int SCALE = 3;
	public String NAME = "";

	public boolean running = false;
	public boolean playing = false;
	public static JFrame frame;
	public Random ran = new Random();
	private int fps;
	private boolean limitFps;
	private boolean CLOSE_REQUESTED = false;
	private GameBase instance;
	private int ups;
	public int gameTicks;

	public void createWindow() {
		Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);

		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);

		frame = new JFrame(NAME);
		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.addWindowListener(new GameCloseHandler(this));
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setVisible(true);
	}

	public void run() {

		double nsPerTick = 1000000000.0 / 60.0;
		double unprocessed = 0;
		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		int frames = 0;
		int ticks = 0;

		baseInit();
		init();
		requestFocus();
		playing = true;
		while (running && !CLOSE_REQUESTED) {

			// if (!hasFocus()) keys.release();
			try {
				long now = System.nanoTime();
				unprocessed += (now - lastTime) / nsPerTick;
				lastTime = now;
				boolean shouldRender = !limitFps;

				while (unprocessed >= 1) {
					// this is local for this loop
					ticks++;
					// this is global forever
					gameTicks++;
					tick();
					unprocessed -= 1;
					shouldRender = true;
				}
				// I am in 2 minds about this. We shall see how things go. But
				// down the track this should not be needed
				// try {
				// Thread.sleep(1L);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }

				if (shouldRender) {
					render();
					frames++;
				}

				if (System.currentTimeMillis() - lastTimer > 1000) {
					lastTimer += 1000;
					this.fps = frames;
					this.ups = ticks;
					System.out.println("Updates " + this.ups + " - Frames " + this.fps);
					frames = 0;
					ticks = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				// Perhaps create some sort of exception overlay??
				System.exit(1);
			}
		}

		System.exit(0);
	}

	private void baseInit() {
		// Create our menu stacks. Set a basic menu. Create the input listeners.
		// Create our custom drawable screen.
	}

	public void limitFps(boolean limit) {
		limitFps = limit;
	}

	public void requestClose() {
		CLOSE_REQUESTED = true;
		System.out.println("Close has been requested on the window");
	}

	public abstract void init();

	public void start() {
		if (this.running == true) {
			throw new UnsupportedOperationException("Game is already started!!");
		}
		running = true;
		Thread t = new Thread(this, "Settling Main Thread");
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	public void rescale(int scale) {
		if (this.playing == false) {
			throw new UnsupportedOperationException("Can't rescale the window while the game is not running. ");
		}
		this.SCALE = scale;
		this.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.pack();
		// frame.setLocationRelativeTo(null);
	}

	public void stop() {
		if (this.running == false) {
			throw new UnsupportedOperationException("Game is not running");
		}
		running = false;
	}

	public void setPixelScale(int scale) {
		this.SCALE = scale;
	}

	public void setName(String name) {
		this.NAME = name;
	}

	public void setSize(int width) {
		this.WIDTH = width;
		this.HEIGHT = width * 3 / 4;
	}

	protected abstract void tick();

	protected abstract void render();

}
