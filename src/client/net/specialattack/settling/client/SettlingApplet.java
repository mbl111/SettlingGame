
package net.specialattack.settling.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

// Tesitng

public class SettlingApplet extends Applet {

    private static final long serialVersionUID = -887417069382703883L;
    private SettlingCanvas canvas;
    private Thread instanceThread;
    private SettlingClient settling;
    protected static SettlingApplet instance;
    private JPanel panel;

    public SettlingApplet() {
        instance = this;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() {
        this.canvas = new SettlingCanvas(this);
        this.panel = new JPanel();

        this.setLayout(new BorderLayout());
        this.panel.setLayout(new BorderLayout());
        this.panel.add(this.canvas, "Center");

        this.add(this.panel, "Center");
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

    public void display(Component component) {
        this.canvas = null;

        this.panel.removeAll();

        this.panel.setLayout(new BorderLayout());
        this.panel.add(component, "Center");
        this.validate();
        this.repaint();
    }

}
