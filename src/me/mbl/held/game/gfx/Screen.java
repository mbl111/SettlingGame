package me.mbl.held.game.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Bitmap {

	public BufferedImage img;
	private int xOff = 0;
	private int yOff = 0;

	public static final int BIT_MIRROR_X = 0x01;
	public static final int BIT_MIRROR_Y = 0x02;

	public Screen(int w, int h) {
		super(w, h);
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		this.w = w;
		this.h = h;
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xFFFFFFFF;
		}
	}

	public void draw(Bitmap bitmap, int x, int y) {
		super.draw(bitmap, x - xOff, y - yOff);
	}

	public void drawWithTint(Bitmap bitmap, int x, int y, int col) {
		super.drawWithTint(bitmap, x - xOff, y - yOff, col);
	}

	// public void drawWithWash(Bitmap bitmap, int x, int y, int color, int
	// scale) {
	// for (int yy = y; yy < y + bitmap.h * scale; yy++) {
	// if (yy + y < 0 || yy + y > h) continue;
	// for (int xx = x; xx < x + bitmap.w * scale; xx++) {
	// if (xx + x < 0 || xx + x > w) continue;
	// int col = bitmap.pixels[((xx - x)/scale) + ((yy - y)/scale) * bitmap.w];
	// if (col < 0) pixels[(xx + x) + (yy + y) * w] = col & color;
	// }
	// }
	// }

	public void drawColor(int x, int y, int w, int h, int color) {
		super.drawColor(x - xOff, y - yOff, w, h, color);
	}

	public void setOffset(int xScroll, int yScroll) {
		this.xOff = xScroll;
		this.yOff = yScroll;
	}

	public void clear(int col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col;
		}

	}

	public void drawIsoRect(int x, int y, int color) {
		super.drawIsoRect(x - xOff, y - yOff, color);
	}

	public void drawIso(Bitmap bitmap, int x, int y) {
		super.drawIso(bitmap, x - xOff, y - yOff);
	}

	public void setPixel(int x, int y, int color) {
		super.setPixel(x - xOff, y - yOff, color);
	}

}
