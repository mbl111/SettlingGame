
package net.specialattack.settling.client;

import java.awt.Canvas;

public class SettlingCanvas extends Canvas {
    private static final long serialVersionUID = -5922116182846025254L;
    private final SettlingApplet applet;

    public SettlingCanvas(SettlingApplet applet) {
        this.applet = applet;
    }

    @Override
    public synchronized void addNotify() {
        super.addNotify();
        this.applet.startSettling();
    }

    @Override
    public synchronized void removeNotify() {
        this.applet.shutdown();
        super.removeNotify();
    }
}
