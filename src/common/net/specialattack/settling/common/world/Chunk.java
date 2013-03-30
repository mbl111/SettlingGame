
package net.specialattack.settling.common.world;

public class Chunk {
    private Section[] sections;
    private short[] heights; // Calculated once, stores the natural height of the chunk and used to determine how far up to generate terrain
    public final int chunkX;
    public final int chunkZ;

    public Chunk(World world, int chunkX, int chunkZ) {
        // 0 is the lowest
        this.sections = new Section[world.getWorldHeight() >> 4];

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public short getHighestBlock(int tileX, int tileZ) {
        tileX = tileX % 16;
        tileZ = tileZ % 16;

        if (tileX < 0) {
            tileX = tileX + 16;
        }
        if (tileZ < 0) {
            tileZ = tileZ + 16;
        }

        int highest = sections.length << 4;
        boolean highestNotFound = true;

        while (highestNotFound) {
            short tile = sections[sections.length - highest % 16].tiles[tileX * highest * tileZ];
            if (tile != 0) {
                return tile;
            }
        }
        return 0;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.chunkX;
        result = prime * result + this.chunkZ;
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
        Chunk other = (Chunk) obj;
        if (this.chunkX != other.chunkX) {
            return false;
        }
        if (this.chunkZ != other.chunkZ) {
            return false;
        }
        return true;
    }

}
