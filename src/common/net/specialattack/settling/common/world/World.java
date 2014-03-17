
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

    public abstract int getMinChunkXBorder();

    public abstract int getMaxChunkXBorder();

    public abstract int getMinChunkZBorder();

    public abstract int getMaxChunkZBorder();

    public abstract Section getSection(int sectionX, int sectionZ);

    public Chunk getChunkAtTile(int tileX, int tileZ, boolean generateIfMissing) {
        int chunkX = tileX >> 4;
        int chunkZ = tileZ >> 4;

        if (chunkX < this.getMinChunkXBorder() || chunkX > this.getMaxChunkXBorder()) {
            Settling.log.warning("Request for out of reach chunk.");
            return null;
        }
        if (chunkZ < this.getMinChunkZBorder() || chunkZ > this.getMaxChunkZBorder()) {
            Settling.log.warning("Request for out of reach chunk.");
            return null;
        }

        return this.getChunk(chunkX, chunkZ, generateIfMissing);
    }

    public Chunk getChunk(int chunkX, int chunkZ, boolean generateIfMissing) {
        Section section = this.getSectionAtChunk(chunkX, chunkZ);

        chunkX = chunkX % 16;
        if (chunkX < 0)
            chunkX += 16;

        chunkZ = chunkZ % 16;
        if (chunkZ < 0)
            chunkZ += 16;

        return section.getChunk(chunkX, chunkZ, generateIfMissing);
    }

    public Section getSectionAtTile(int tileX, int tileZ) {
        int chunkX = tileX >> 4;
        int chunkZ = tileZ >> 4;

        if (chunkX < this.getMinChunkXBorder() || chunkX > this.getMaxChunkXBorder()) {
            Settling.log.warning("Request for out of reach section.");
            return null;
        }
        if (chunkZ < this.getMinChunkZBorder() || chunkZ > this.getMaxChunkZBorder()) {
            Settling.log.warning("Request for out of reach section.");
            return null;
        }

        return this.getSection(chunkX >> 4, chunkZ >> 4);
    }

    public Section getSectionAtChunk(int chunkX, int chunkZ) {
        if (chunkX < this.getMinChunkXBorder() || chunkX > this.getMaxChunkXBorder()) {
            Settling.log.warning("Request for out of reach section.");
            return null;
        }
        if (chunkZ < this.getMinChunkZBorder() || chunkZ > this.getMaxChunkZBorder()) {
            Settling.log.warning("Request for out of reach section.");
            return null;
        }

        return this.getSection(chunkX >> 4, chunkZ >> 4);
    }

    public boolean isLandAt(int tileX, int tileZ) {
        Chunk chunk = this.getChunkAtTile(tileX, tileZ, false);

        if (chunk != null) {
            int x = tileX % 16;
            if (x < 0)
                x += 16;
            int z = tileZ % 16;
            if (z < 0)
                z += 16;

            return chunk.isLandAt(x, z);
        }

        return false;
    }

}
