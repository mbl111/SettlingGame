package net.specialattack.settling.launcher;

import java.applet.Applet;

import net.specialattack.settling.SettlingApplet;

public class InlineLauncher extends LauncherStub {

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/lib/natives/windows/");

		LauncherFrame.main(args, new InlineLauncher());
	}

	@Override
	public Applet launchGame() {
		return new SettlingApplet();
	}

}
