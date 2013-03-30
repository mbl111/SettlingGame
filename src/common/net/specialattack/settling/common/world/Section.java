
package net.specialattack.settling.common.world;

public class Section {
    public short[] tiles;
    public short[] colors;
    public final Chunk chunk;
    public final int sectionY;

    public Section(Chunk chunk, int sectionY) {
        this.chunk = chunk;
        this.sectionY = sectionY;

        this.tiles = new short[4096]; // 16 * 16 * 16
        this.colors = new short[4096]; // 16 * 16 * 16
    }
}
