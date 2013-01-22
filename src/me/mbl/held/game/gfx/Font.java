package me.mbl.held.game.gfx;

public class Font {

	public static final String chars = "" + //
			"ABCDEFGHIJKLMNOP" + //
			"QRSTUVWXYZ:[]!? " + //
			"&";

	public static final int[] charWidth = {//
	8, 8, 8, 8, 8, 7, 8, 8, 7, 8, 8, 8, 8, 8, 8, 7,//
			8, 8, 8, 7, 8, 8, 8, 8, 8, 8, 3, 4, 4, 5, 6, 4, 0, 0 //
	};

	public static final int[] hexValues = { 0xFF99FF44, 0xFF00FFFF, 0xFFFF4444, 0xFFFF55FF, 0xFFFFFF00, 0xFFFFFFFF };

	public static final int FONT_SHEET_WIDTH = 16;

	public static void draw(String msg, Screen screen, int x, int y, int color) {
		if (msg != null) {
			int offs = 0;
			if (msg.length() > 0) {
				msg = msg.toUpperCase();
				for (int i = 0; i < msg.length(); i++) {
					int ix = chars.indexOf(msg.charAt(i));

					if (ix == 32) {
						if (chars.indexOf(msg.charAt(i + 1)) <=  6) {
							color = hexValues[chars.indexOf(msg.charAt(i + 1))];
							msg = msg.replaceFirst("&[A-F]", " ");
						}
					}

					if (ix >= 0) {
						screen.drawWithTint(Art.font[ix % FONT_SHEET_WIDTH][ix / FONT_SHEET_WIDTH], x + offs, y, color);
						offs += charWidth[ix];
					}
				}
			}
		}
	}

	public static void drawWithShadow(String msg, Screen screen, int x, int y, int color) {
		if (msg != null) {
			int offs = 0;
			if (msg.length() > 0) {
				msg = msg.toUpperCase();
				for (int i = 0; i < msg.length(); i++) {
					int ix = chars.indexOf(msg.charAt(i));

					if (ix == 32) {
						if (chars.indexOf(msg.charAt(i + 1)) < 6) {
							color = hexValues[chars.indexOf(msg.charAt(i + 1))];
							msg = msg.replaceFirst("&[A-F]", " ");
							continue;
						}
					}

					if (ix >= 0) {
						screen.drawWithTint(Art.font[ix % FONT_SHEET_WIDTH][ix / FONT_SHEET_WIDTH], x + offs, y + 1, 0xFF555555);
						screen.drawWithTint(Art.font[ix % FONT_SHEET_WIDTH][ix / FONT_SHEET_WIDTH], x + offs, y, color);
						offs += charWidth[ix];
					}
				}
			}
		}
	}
}
