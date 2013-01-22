package me.mbl.held.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.mbl.held.game.Game;

public class Art {

	public static final Bitmap[][] tiles = cut("/tiles.png", 32, 32);
	public static final Bitmap[][] font = cut("/font.png", 8, 8);
	
	private static Bitmap[][] cut(String path, int w, int h, int bx, int by) {
		try {
			BufferedImage bi = ImageIO.read(Game.class.getResource(path));

			int xTiles = (bi.getWidth() - bx) / w;
			int yTiles = (bi.getHeight() - by) / h;

			Bitmap[][] result = new Bitmap[xTiles][yTiles];

			for (int x = 0; x < xTiles; x++) {
				for (int y = 0; y < yTiles; y++) {
					result[x][y] = new Bitmap(w, h);
					bi.getRGB(bx + x * w, by + y * h, w, h, result[x][y].pixels, 0, w);
				}
			}

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Bitmap[][] cut(String path, int w, int h) {
		return cut(path, w, h, 0, 0);
	}

}
