
package net.specialattack.settling.launcher;

import java.applet.Applet;

import net.specialattack.settling.SettlingApplet;

public class InlineLauncher extends LauncherStub {

    private static final long serialVersionUID = -6105682158910098389L;

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/lib/natives/windows/");
        System.setProperty("sun.java2d.noddraw", "true");
        System.setProperty("sun.java2d.opengl", "false");

        LauncherFrame.main(args, new InlineLauncher());
    }

    @Override
    public Applet launchGame() {
        return new SettlingApplet();
    }

}
