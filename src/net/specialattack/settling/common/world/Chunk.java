
package net.specialattack.settling.common.world;

import net.specialattack.settling.client.rendering.TileRenderer;
import net.specialattack.settling.common.item.ItemTile;
import net.specialattack.settling.common.item.Items;

import org.lwjgl.opengl.GL11;

public class Chunk {
    private Section[] sections;
    private short[] heights; // Calculated once, stores the natural height of the chunk and used to determine how far up to generate terrain
    public final int chunkX;
    public final int chunkZ;

    public Chunk(World world, int chunkX, int chunkZ) {
        this.sections = new Section[world.getWorldHeight() >> 4];

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public short getHeight(int tileX, int tileZ) {
        tileX = tileX % 16;
        tileZ = tileZ % 16;

        if (tileX < 0) {
            tileX = tileX + 16;
        }
        if (tileZ < 0) {
            tileZ = tileZ + 16;
        }

        return this.heights[tileX + tileZ * 16];
    }

    public void populateHeight(short[] heights) {
        this.heights = heights;
    }

    public Section getSection(int section) {
        return null;
    }


    public void tick() {

    }

}
