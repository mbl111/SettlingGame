package me.mbl.held.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import me.mbl.held.game.gfx.Art;
import me.mbl.held.game.gfx.Screen;

public class Game extends GameBase {

	private Screen screen;

	private int max = 50;
	private int min = -50;
	private int posX = min;
	private int posY = min;

	private int[] level;

	@Override
	protected void tick() {
		if (gameTicks % 2 == 0) return;
		if (posY == min && posX < max) {
			posX++;
		} else if (posY < max && posX == max) {
			posY++;
		} else if (posY == max && posX > min) {
			posX--;
		} else {
			posY--;
		}
	}

	@Override
	protected void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		// Do render stuff here;
		// for (int x = 0; x < screen.w; x++) {
		// for (int y = 0; y < screen.h; y++) {
		// screen.setPixel(x, y, (0xFFFFFF & ran.nextInt()) | 0xFF000000);
		// }
		// }
		screen.clear(0xFF000000);
		screen.setOffset(posX, posY);
		int tileSize = 32;
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {

				// 4/2 == 4 >> 1. However the bitshifting is faster. Just be
				// sure to bracket it!
				int xx = (WIDTH >> 1) + x * (tileSize) - y * (tileSize);
				int yy = x * (tileSize >> 1) + y * (tileSize >> 1) - 30;

				screen.drawIso(Art.tiles[level[x + y * 16]][0], xx, yy);
				// , 0xFF000000 | ((x +
				// 1) * (255 / 23)) <<
				// 16 | ((y + 1) * (255
				// / 23)) << 8 | 0xFF);
			}
		}

		screen.draw(Art.tiles[2][0], 50, 50);

		screen.setOffset(0, 0);

		screen.draw(Art.font[0][0], 0, 0);
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		int ww = WIDTH * SCALE;
		int hh = HEIGHT * SCALE;
		int xo = (getWidth() - ww) / 2;
		int yo = (getHeight() - hh) / 2;
		g.drawImage(screen.img, xo, yo, ww, hh, null);
		g.dispose();
		bs.show();
	}

	@Override
	public void init() {
		screen = new Screen(WIDTH, HEIGHT);
		level = new int[16 * 16];
		for (int i = 0; i < level.length; i++) {
			level[i] = 0;
			if (ran.nextInt(5) == 0) level[i] = 1;
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setSize(400);
		game.setName("Isometric Render");
		game.SCALE = 3;
		game.createWindow();
		game.start();
	}

}
