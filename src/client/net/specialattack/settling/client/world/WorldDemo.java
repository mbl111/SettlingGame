
package net.specialattack.settling.client.world;

import java.io.File;

import net.specialattack.settling.client.world.gen.TestWorldGenerator;
import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.item.Items;
import net.specialattack.settling.common.world.Chunk;
import net.specialattack.settling.common.world.Section;
import net.specialattack.settling.common.world.World;

public class WorldDemo extends World {

    private Chunk[] chunks;
    private TestWorldGenerator generator;

    public WorldDemo(File saveFolder) {
        super(saveFolder);

        this.generator = new TestWorldGenerator(System.currentTimeMillis());

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
        Chunk chunk = this.chunks[(chunkX + 8) + (chunkZ + 8) * 16];

        if (chunk != null && chunk.hasBeenGenerated()) {
            return chunk;
        }
        else if (chunk != null && generateIfMissing) {
            Settling.log.finest("Generating chunk @" + chunkX + ";" + chunkZ);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < chunk.getHeight(x, y); y++) {
                        chunk.setTileAt(x, y, z, Items.dirt.identifier);
                    }
                }
            }

            chunk.setGenerated();

            return chunk;
        }

        return null;
    }

    @Override
    public Section getSectionAt(int chunkX, int chunkZ, int sectionY) {
        return this.getChunkAt(chunkX, chunkZ, false).getSection(sectionY);
    }

}
