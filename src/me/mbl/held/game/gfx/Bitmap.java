package me.mbl.held.game.gfx;

import java.util.Arrays;

public class Bitmap {

	public int[] pixels;
	public int h;
	public int w;

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		this.pixels = new int[w * h];
	}

	public void clear(int color) {
		Arrays.fill(pixels, color);
	}

	public void drawColor(int x, int y, int w, int h, int color) {
		int x0 = x;
		int x1 = x + w;
		int y0 = y;
		int y1 = y + h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > this.w) x1 = this.w;
		if (y1 > this.h) y1 = this.h;
		int ww = x1 - x0;

		for (int yy = y0; yy < y1; yy++) {
			int tp = yy * this.w + x0;
			int sp = (yy - y) * this.w + (x0 - x);
			tp -= sp;
			for (int xx = sp; xx < sp + ww; xx++) {
				pixels[tp + xx] = color;
			}
		}
	}

	public void draw(Bitmap bitmap, int x, int y) {
		int x0 = x;
		int x1 = x + bitmap.w;
		int y0 = y;
		int y1 = y + bitmap.h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;
		int ww = x1 - x0;

		for (int yy = y0; yy < y1; yy++) {
			int tp = yy * w + x0;
			int sp = (yy - y) * bitmap.w + (x0 - x);
			tp -= sp;
			for (int xx = sp; xx < sp + ww; xx++) {
				int col = bitmap.pixels[xx];
				if (col < 0) pixels[tp + xx] = col;
			}
		}
	}

	public void drawWithTint(Bitmap bitmap, int x, int y, int tint) {
		int x0 = x;
		int x1 = x + bitmap.w;
		int y0 = y;
		int y1 = y + bitmap.h;
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;
		int ww = x1 - x0;

		for (int yy = y0; yy < y1; yy++) {
			int tp = yy * w + x0;
			int sp = (yy - y) * bitmap.w + (x0 - x);
			tp -= sp;
			for (int xx = sp; xx < sp + ww; xx++) {
				int col = bitmap.pixels[xx];
				if (col < 0) pixels[tp + xx] = col & tint;
			}
		}
	}

	public void drawIsoRect(int x, int y, int color) {
		int tileWidth = 32;
		// int rh = tileWidth;
		//
		// int tp = (int) tileWidth / 2 - 1;
		//
		// int cw = 1;
		//
		// for (int yy = 0; yy < rh; yy++) {
		// for (int xx = 0; xx <= cw; xx++) {
		//
		// int px = x + (xx - cw / 2);
		// int py = y + yy;
		// if (px < w && px >= 0 && py < h && py >= 0){
		// pixels[px + py * w] = color;
		// }
		//
		// }
		//
		// if (yy >= tp) {
		// cw -= 4;
		// } else {
		// cw += 4;
		// }
		//
		// }

		for (int xx = 0; xx < tileWidth; xx++) {
			for (int yy = 0; yy < tileWidth; yy++) {

				int px = ((xx * (2) - yy * (2)) >> 1) + x;
				int py = ((xx + yy) >> 1) + 1 + y;
				if (px < w && px >= 0 && py < h && py >= 0) {
					pixels[px + py * w] = color;
				}

			}
		}

	}

	public void drawIso(Bitmap bitmap, int x, int y) {
		int tileWidth = 32;
		for (int yy = 0; yy < tileWidth; yy++) {
			for (int xx = 0; xx < tileWidth; xx++) {

				int px = ((xx * (2) - yy * (2)) >> 1) + x;
				int py = ((xx + yy) >> 1) + 1 + y;
				if (px < w && px >= 0 && py < h && py >= 0) {
					pixels[px + py * w] = bitmap.pixels[xx + yy * bitmap.w];
				}

			}
		}

	}

	public void setPixel(int x, int y, int color) {
		if (x + y * w > pixels.length) return;
		pixels[x + y * w] = color;
	}

}
