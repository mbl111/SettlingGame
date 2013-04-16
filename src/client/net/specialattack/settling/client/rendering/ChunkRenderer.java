
package net.specialattack.settling.client.rendering;

import net.specialattack.settling.common.item.Item;
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
        this.dirty = true;
    }

    public void createGlChunk() {
        if (this.glCallList < 0) {
            this.glCallList = GL11.glGenLists(1);
        }
        GL11.glNewList(this.glCallList, GL11.GL_COMPILE);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < this.chunk.getNumSections() * 16; y++) {
                    //int rx = this.chunk.chunkX * 16 + x - 16;
                    //int rz = this.chunk.chunkZ * 16 + z - 16;

                    short tile = this.chunk.getTileAt(x, y, z);

                    if (tile > 0) {
                        Item item = Items.itemList[tile];

                        if (item instanceof ItemTile) {
                            if (y == 0) {
                                TileRenderer.renderTileFloor((ItemTile) Items.itemList[tile], x, y, z);
                            }
                        }
                    }
                }
            }
        }
        GL11.glEndList();

        this.dirty = false;
    }

    public void renderChunk() {
        if (this.glCallList > 0 && !this.dirty) {
            GL11.glPushMatrix();
            GL11.glTranslated((float) this.chunk.chunkX * 16.0F, 0.0F, (float) this.chunk.chunkZ * 16.0F);
            GL11.glCallList(this.glCallList);
            GL11.glPopMatrix();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chunk == null) ? 0 : this.chunk.hashCode());
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ChunkRenderer other = (ChunkRenderer) obj;
        if (this.chunk == null) {
            if (other.chunk != null) {
                return false;
            }
        }
        else if (!this.chunk.equals(other.chunk)) {
            return false;
        }
        return true;
    }
}
