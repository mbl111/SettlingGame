package net.specialattack.settling.launcher;

import java.applet.Applet;
import java.io.File;

import net.specialattack.settling.SettlingApplet;

public class InlineLauncher extends LauncherStub {

	public static String seperator = "\\";

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + seperator + "lib" + seperator + "natives" + seperator + "windows" + seperator);
		System.out.println("Loading LWJGL libs from: " + System.getProperty("user.dir") + seperator + "lib" + seperator + "natives" + seperator + "windows" + seperator);
		LauncherFrame.main(args, new InlineLauncher());
	}

	@Override
	public Applet launchGame() {
		return new SettlingApplet();
	}

}
