
package net.specialattack.settling.launcher;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;

public abstract class LauncherStub extends Applet implements AppletStub {

    private static final long serialVersionUID = -1692266697837148613L;
    private Applet applet;

    public abstract Applet launchGame();

    public void setApplet(Applet applet) {
        this.applet = applet;
        this.applet.setStub(this);
        this.applet.setSize(this.getWidth(), this.getHeight());

        this.setLayout(new BorderLayout());
        this.add(applet, "Center");

        this.applet.init();
        this.applet.start();
        this.validate();
    }

    @Override
    public boolean isActive() {
        return this.applet.isActive();
    }

    @Override
    public void appletResize(int width, int height) {}

    @Override
    public void stop() {
        this.applet.stop();
    }

    @Override
    public void destroy() {
        this.applet.destroy();
    }
}
