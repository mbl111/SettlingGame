
package net.specialattack.settling.client.rendering;

import net.specialattack.settling.common.item.ItemTile;
import net.specialattack.settling.common.item.Items;
import net.specialattack.settling.common.world.Chunk;

import org.lwjgl.opengl.GL11;

public class ChunkRenderer {

    public int glCallList = -1;
    public final Chunk chunk;

    public ChunkRenderer(Chunk chunk) {
        this.chunk = chunk;
    }

    public void createGlChunk() {
        ItemTile grass = Items.grass;
        glCallList = GL11.glGenLists(1);
        GL11.glNewList(glCallList, GL11.GL_COMPILE);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                TileRenderer.renderTileFloor(grass, chunk.chunkX * 16 + x - 16, chunk.getHeight(chunk.chunkX * 16 + x, chunk.chunkZ * 16 + z), chunk.chunkZ * 16 + z - 16);
            }
        }
        GL11.glEndList();
    }

    public void renderChunk() {
        if (glCallList > 0) {
            GL11.glCallList(glCallList);
        }
    }
}
