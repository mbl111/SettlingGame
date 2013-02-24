
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

        this.chunks = new Chunk[64];

        for (int x = 0; x < 8; x++) {
            for (int z = 0; z < 8; z++) {
                this.chunks[x + z * 8] = new Chunk(this, x - 3, z - 3);

                this.chunks[x + z * 8].populateHeight(this.generator.getHeights(x - 3, z - 3));
            }
        }
    }

    @Override
    public boolean isLimited() {
        return true;
    }

    @Override
    public int getMinXBorder() {
        return -64;
    }

    @Override
    public int getMaxXBorder() {
        return 64;
    }

    @Override
    public int getMinZBorder() {
        return -64;
    }

    @Override
    public int getMaxZBorder() {
        return 64;
    }

    @Override
    public short getWorldHeight() {
        return 512;
    }

    @Override
    public Chunk getChunkAt(int chunkX, int chunkZ, boolean generateIfMissing) {
        return this.chunks[(chunkX + 4) + (chunkZ + 4) * 8];
    }

    @Override
    public Section getSectionAt(int chunkX, int chunkZ, int sectionY) {
        return this.getChunkAt(chunkX, chunkZ, false).getSection(sectionY);
    }

}
