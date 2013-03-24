
package net.specialattack.settling.client.rendering;

import net.specialattack.settling.common.item.ItemTile;
import net.specialattack.settling.common.item.Items;
import net.specialattack.settling.common.world.Chunk;

import org.lwjgl.opengl.GL11;

public class ChunkRenderer {

    public int glCallList = -1;
    public boolean dirty = true;
    public final Chunk chunk;

    public ChunkRenderer(Chunk chunk) {
        this.chunk = chunk;
    }

    public void markDirty() {
        dirty = true;
    }

    public void createGlChunk() {
        ItemTile grass = Items.grass;
        if (glCallList < 0) {
            glCallList = GL11.glGenLists(1);
        }
        GL11.glNewList(glCallList, GL11.GL_COMPILE);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int height = chunk.getHeight(chunk.chunkX * 16 + x, chunk.chunkZ * 16 + z);
                int rx = chunk.chunkX * 16 + x - 16;
                int rz = chunk.chunkZ * 16 + z - 16;

                TileRenderer.renderTileNorthFace(grass, rx, height, rz);
                TileRenderer.renderTileFloor(grass, rx, height, rz);
            }
        }
        GL11.glEndList();

        dirty = false;
    }

    public void renderChunk() {
        if (glCallList > 0 && !dirty) {
            GL11.glCallList(glCallList);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chunk == null) ? 0 : chunk.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ChunkRenderer other = (ChunkRenderer) obj;
        if (chunk == null) {
            if (other.chunk != null) {
                return false;
            }
        }
        else if (!chunk.equals(other.chunk)) {
            return false;
        }
        return true;
    }
}
