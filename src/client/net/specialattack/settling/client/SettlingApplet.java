
package net.specialattack.settling.client;

import java.applet.Applet;
import java.awt.BorderLayout;

public class SettlingApplet extends Applet {
    private static final long serialVersionUID = -887417069382703883L;
    private SettlingCanvas canvas;
    private Thread instanceThread;
    private SettlingClient settling;

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

        this.settling = new SettlingClient();

        this.settling.setCanvas(this.canvas);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void start() {
        this.startSettling();
    }

    @Override
    public void stop() {
        this.shutdown();

        System.exit(0);
    }

    @Override
    public boolean requestFocusInWindow() {
        if (super.requestFocusInWindow()) {
            this.canvas.requestFocusInWindow();

            return true;
        }

        return false;
    }

    public void startSettling() {
        if (this.settling != null && this.instanceThread == null) {
            this.instanceThread = new Thread(this.settling, "Settling Main Thread");
            this.instanceThread.start();
        }
    }

    public void shutdown() {
        if (this.instanceThread != null) {
            this.settling.attemptShutdown();

            try {
                this.instanceThread.join(10000L);
            }
            catch (InterruptedException e) {}
            finally {
                this.instanceThread = null;
            }
        }
    }
}
