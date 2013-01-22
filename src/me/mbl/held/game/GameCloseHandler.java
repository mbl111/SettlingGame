package me.mbl.held.game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameCloseHandler implements WindowListener {

	private GameBase game;

	public GameCloseHandler(GameBase gameBase) {
		this.game = gameBase;
	}

	public void windowActivated(WindowEvent e) {

	}

	public void windowClosed(WindowEvent e) {

	}

	public void windowClosing(WindowEvent e) {

		System.out.println("Window Closing");
		game.requestClose();
	}

	public void windowDeactivated(WindowEvent e) {

	}

	public void windowDeiconified(WindowEvent e) {

	}

	public void windowIconified(WindowEvent e) {

	}

	public void windowOpened(WindowEvent e) {

	}

}
