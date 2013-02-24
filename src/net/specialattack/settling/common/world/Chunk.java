
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

    private int chunkRenderList;

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

        return heights[tileX + tileZ * 16];
    }

    public void populateHeight(short[] heights) {
        this.heights = heights;
        tileUpdate();
    }

    public Section getSection(int section) {
        return null;
    }

    public void tileUpdate() {
        ItemTile grass = Items.grass;
        chunkRenderList = GL11.glGenLists(1);
        GL11.glNewList(chunkRenderList, GL11.GL_COMPILE);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                TileRenderer.renderTileFloor(grass, chunkX * 16 + x - 16, getHeight(chunkX * 16 + x, chunkZ * 16 + z),chunkZ * 16 + z - 16);
            }
        }
        GL11.glEndList();
    }

    public void tick() {

    }

    public void render() {
        GL11.glCallList(chunkRenderList);
    }
}
