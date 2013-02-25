
package net.specialattack.settling.client.world;

import java.io.File;

import net.specialattack.settling.common.world.Chunk;
import net.specialattack.settling.common.world.Section;
import net.specialattack.settling.common.world.World;
import net.specialattack.settling.common.world.gen.WorldGenerator;

public class WorldDemo extends World {

    private Chunk[] chunks;
    private WorldGenerator generator;

    public WorldDemo(File saveFolder) {
        super(saveFolder);

        this.generator = new WorldGenerator(100L);

        this.chunks = new Chunk[265];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                this.chunks[x + z * 16] = new Chunk(this, x - 7, z - 7);

                this.chunks[x + z * 16].populateHeight(this.generator.getHeights(x - 7, z - 7));
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
        return this.chunks[(chunkX + 8) + (chunkZ + 8) * 16];
    }

    @Override
    public Section getSectionAt(int chunkX, int chunkZ, int sectionY) {
        return this.getChunkAt(chunkX, chunkZ, false).getSection(sectionY);
    }

}
