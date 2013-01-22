package me.mbl.held.game;

import java.applet.Applet;
import java.awt.BorderLayout;

public class GameApplet extends Applet {

	private static final long serialVersionUID = 1L;

	public Game game;

	public void init() {
		game = new Game();
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

}
