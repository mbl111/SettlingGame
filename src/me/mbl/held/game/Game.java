package me.mbl.held.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import me.mbl.held.game.gfx.Art;
import me.mbl.held.game.gfx.Font;
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

		screen.draw(Art.tiles[2][0], 100, 100);

		screen.setOffset(0, 0);

		Font.draw("Good morning", screen, 0, 0, 0xFF00FF00);
		Font.drawWithShadow("remind you of minecraft with the shadow??", screen, 0, 8, 0xFFFFFFFF);
		Font.drawWithShadow("&aremind &byou &cof &dminecraft &ewith &fthe shadow??", screen, 0, 8 * 2, 0xFFFFFFFF);

		// Slightly annoying that there is no alpha support... yet ;)
		screen.drawColor(0, screen.h - 32 - 2 - 8, 196, 16 + 4, 0xFF222222);

		Font.drawWithShadow("[T]", screen, 0, screen.h - 32, 0xFFFF0000);
		Font.drawWithShadow("mbl:", screen, 16, screen.h - 32, 0xFF33AAFF);
		Font.drawWithShadow(" Hey!!", screen, 16 + 27, screen.h - 32, 0xFFFFFFFF);

		Font.drawWithShadow("[Supop]", screen, 0, screen.h - 32 - 9, 0xFF00FFFF);
		Font.drawWithShadow("heldplayer:", screen, 48, screen.h - 32 - 9, 0xFF33AAFF);
		Font.drawWithShadow(" Hi mbl", screen, 48 + 83, screen.h - 32 - 9, 0xFFFFFFFF);

		Font.draw("abcdefghijklmnopqrstuvwxyz[]:?!", screen, 0, 8 * 4, 0xFF00FFFF);
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		int ww = WIDTH * SCALE;
		int hh = HEIGHT * SCALE;
		int xo = (getWidth() - ww) >> 1;
		int yo = (getHeight() - hh) >> 1;
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
		game.setSize(480);
		game.setName("Isometric Render");
		game.SCALE = 2;
		game.createWindow();
		game.start();
	}

}
