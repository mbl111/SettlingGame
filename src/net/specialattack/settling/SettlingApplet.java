package net.specialattack.settling;

import java.applet.Applet;
import java.awt.BorderLayout;

public class SettlingApplet extends Applet {
	private static final long serialVersionUID = -887417069382703883L;
	private SettlingCanvas canvas;
	private Thread instanceThread;
	private Settling settling;

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() {
		this.canvas = new SettlingCanvas(this);
		this.setLayout(new BorderLayout());
		this.add(this.canvas, "Center");
		this.canvas.setFocusable(true);
		this.canvas.setFocusTraversalKeysEnabled(false);
		this.validate();

		this.settling = new Settling();

		this.settling.setCanvas(canvas);
	}

	@Override
	public void start() {
		startSettling();
	}

	@Override
	public void stop() {
		shutdown();
	}

	public void startSettling() {
		if (this.settling != null && this.instanceThread == null) {
			this.instanceThread = new Thread(this.settling, "Settling Main Thread");
			this.instanceThread.start();
		}
	}

	public void shutdown() {
		if (this.instanceThread != null) {
			settling.attemptShutdown();

			try {
				instanceThread.join(10000L);
			} catch (InterruptedException e) {
			} finally {
				instanceThread = null;
			}
		}
	}
}
