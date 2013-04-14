
package net.specialattack.settling.common.world;

import java.io.File;

import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.world.gen.WorldGenLayer;

public abstract class World {

    protected File saveFolder;
    protected long seed;
    protected WorldGenLayer genLayer;

    public World(File saveFolder) {
        this.saveFolder = saveFolder;
    }

    public abstract boolean isLimited();

    public abstract int getMinXBorder();

    public abstract int getMaxXBorder();

    public abstract int getMinZBorder();

    public abstract int getMaxZBorder();

    public abstract Chunk getChunkAt(int chunkX, int chunkZ, boolean generateIfMissing);

    public Chunk getChunkAtTile(int tileX, int tileZ) {
        if (tileX < this.getMinXBorder() || tileX > this.getMaxXBorder()) {
            Settling.log.warning("Request for out of reach tile.");
            return null;
        }
        if (tileZ < this.getMinZBorder() || tileZ > this.getMaxZBorder()) {
            Settling.log.warning("Request for out of reach tile.");
            return null;
        }

        return this.getChunkAt(tileX >> 4, tileZ >> 4, false);
    }
}
