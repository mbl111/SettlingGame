
package net.specialattack.settling.client.world;

import java.io.File;

import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.world.Chunk;
import net.specialattack.settling.common.world.World;
import net.specialattack.settling.common.world.gen.WorldGenLayerFuzzyZoom;
import net.specialattack.settling.common.world.gen.WorldGenLayerIslands;
import net.specialattack.settling.common.world.gen.WorldGenLayerZoom;

public class WorldDemo extends World {

    private Chunk[] chunks;

    public WorldDemo(File saveFolder) {
        super(saveFolder);

        this.genLayer = new WorldGenLayerIslands(1L);
        this.genLayer = new WorldGenLayerFuzzyZoom(1000L, this.genLayer);
        this.genLayer = WorldGenLayerZoom.zoom(2000L, genLayer, 5);

        this.genLayer.initGlobalSeed(100L);

        this.chunks = new Chunk[265];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                this.chunks[x + z * 16] = new Chunk(this, x - 7, z - 7);
            }
        }
    }

    @Override
    public boolean isLimited() {
        return true;
    }

    @Override
    public int getMinXBorder() {
        return -128;
    }

    @Override
    public int getMaxXBorder() {
        return 128;
    }

    @Override
    public int getMinZBorder() {
        return -128;
    }

    @Override
    public int getMaxZBorder() {
        return 128;
    }

    @Override
    public short getWorldHeight() {
        return 512;
    }

    @Override
    public Chunk getChunkAt(int chunkX, int chunkZ, boolean generateIfMissing) {
        Chunk chunk = this.chunks[(chunkX + 8) + (chunkZ + 8) * 16];

        if (chunk != null && chunk.hasBeenGenerated()) {
            return chunk;
        }
        else if (chunk != null && generateIfMissing) {
            Settling.log.finest("Generating chunk @" + chunkX + ";" + chunkZ);

            chunk.generate();

            return chunk;
        }

        return null;
    }

}
