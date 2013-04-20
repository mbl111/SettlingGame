
package net.specialattack.settling.client.world;

import java.io.File;

import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.world.Chunk;
import net.specialattack.settling.common.world.Section;
import net.specialattack.settling.common.world.World;
import net.specialattack.settling.common.world.gen.WorldGenLayerFuzzyZoom;
import net.specialattack.settling.common.world.gen.WorldGenLayerIslands;
import net.specialattack.settling.common.world.gen.WorldGenLayerZoom;

public class WorldDemo extends World {

    private Chunk[] chunks;

    public WorldDemo(File saveFolder) {
        super(saveFolder);

        this.genLayer = new WorldGenLayerIslands(1L, 20); // Island frequency
        this.genLayer = WorldGenLayerFuzzyZoom.zoom(1000L, this.genLayer, 2); // Island randomness
        this.genLayer = WorldGenLayerZoom.zoom(2000L, this.genLayer, 3); // Island size

        this.genLayer.initGlobalSeed(100L);

        this.chunks = new Chunk[(this.getMaxXBorder() - this.getMinXBorder()) * (this.getMaxZBorder() - this.getMinZBorder()) / 256];

        for (int x = 0; x < (this.getMaxXBorder() - this.getMinXBorder()) / 16; x++) {
            for (int z = 0; z < (this.getMaxZBorder() - this.getMinZBorder()) / 16; z++) {
                this.chunks[x + z * (this.getMaxXBorder() - this.getMinXBorder()) / 16] = new Chunk(this, x + this.getMinXBorder() / 16, z + this.getMinZBorder() / 16);
                this.chunks[x + z * (this.getMaxXBorder() - this.getMinXBorder()) / 16].generate();
            }
        }
    }

    @Override
    public boolean isLimited() {
        return true;
    }

    @Override
    public int getMinXBorder() {
        return -256;
    }

    @Override
    public int getMaxXBorder() {
        return 256;
    }

    @Override
    public int getMinZBorder() {
        return -256;
    }

    @Override
    public int getMaxZBorder() {
        return 256;
    }

    @Override
    public short getWorldHeight() {
        return 512;
    }

    @Override
    public Chunk getChunkAt(int chunkX, int chunkZ, boolean generateIfMissing) {
        int index = (chunkX - (this.getMinXBorder() >> 4)) + (chunkZ - (this.getMinZBorder() >> 4)) * (this.getMaxXBorder() - this.getMinXBorder()) / 16;

        if (index < 0 || index >= this.chunks.length) {
            Settling.log.warning("Incorrect chunk location: (" + chunkX + "; " + chunkZ + ")");
            Settling.log.warning("Relative: (" + (chunkX - this.getMinXBorder() >> 4) + "; " + (chunkZ - this.getMinZBorder() >> 4) + ")");
            Settling.log.warning("Index: " + index + " Size: " + this.chunks.length);

            return null;
        }

        Chunk chunk = this.chunks[index];

        if (chunk != null && chunk.hasBeenGenerated()) {
            return chunk;
        }
        else if (chunk != null && generateIfMissing) {
            chunk.generate();

            return chunk;
        }

        return null;
    }

    @Override
    public Section getSectionAt(int chunkX, int chunkZ, int sectionY) {
        return this.getChunkAt(chunkX, chunkZ, false).getSection(sectionY);
    }

}
