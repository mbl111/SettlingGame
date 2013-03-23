
package net.specialattack.settling.common.world;

public class Section {
    public short[] tiles;
    public final Chunk chunk;
    public final int sectionY;

    public Section(Chunk chunk, int sectionY) {
        this.chunk = chunk;
        this.sectionY = sectionY;

        this.tiles = new short[4096]; // 16 * 16 * 16
    }
}
