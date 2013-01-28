
package net.specialattack.settling.common.world;

import java.io.File;

public abstract class World {

    protected File saveFolder;
    protected long seed;

    public World(File saveFolder) {
        this.saveFolder = saveFolder;

        if (this.getWorldHeight() % 16 != 0) {
            throw new RuntimeException("World height has to be a multiple of 16!");
        }
    }

    public abstract boolean isLimited();

    public abstract int getMinXBorder();

    public abstract int getMaxXBorder();

    public abstract int getMinZBorder();

    public abstract int getMaxZBorder();

    public abstract short getWorldHeight();

    public abstract Chunk getChunkAt(int chunkX, int chunkZ, boolean generateIfMissing);

    public abstract Section getSectionAt(int chunkX, int chunkZ, int sectionY);

    public Chunk getChunkAtTile(int tileX, int tileZ) {
        if (tileX < this.getMinXBorder() || tileX > this.getMaxXBorder()) {
            System.err.println("Request for out of reach tile.");
            return null;
        }
        if (tileZ < this.getMinZBorder() || tileZ > this.getMaxZBorder()) {
            System.err.println("Request for out of reach tile.");
            return null;
        }

        return this.getChunkAt(tileX >> 4, tileZ >> 4, false);
    }

    public Section getSectionAtTile(int tileX, int tileY, int tileZ) {
        if (tileX < this.getMinXBorder() || tileX > this.getMaxXBorder()) {
            System.err.println("Request for out of reach tile.");
            return null;
        }
        if (tileZ < this.getMinZBorder() || tileZ > this.getMaxZBorder()) {
            System.err.println("Request for out of reach tile.");
            return null;
        }
        if (tileY > this.getWorldHeight()) {
            System.err.println("Request for out of reach tile.");
            return null;
        }

        return this.getSectionAt(tileX >> 4, tileZ >> 4, tileY >> 4);
    }

}
