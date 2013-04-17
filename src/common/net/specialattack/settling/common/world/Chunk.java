
package net.specialattack.settling.common.world;

public class Chunk {
    private Section[] sections;
    private short[] heights; // Calculated once, stores the natural height of the chunk and used to determine how far up to generate terrain
    public final int chunkX;
    public final int chunkZ;
    public final World world;
    private boolean isGenerated = false;

    public Chunk(World world, int chunkX, int chunkZ) {
        // 0 is the lowest
        this.sections = new Section[world.getWorldHeight() >> 4];

        for (int i = 0; i < this.sections.length; i++) {
            this.sections[i] = new Section(this, i);
        }

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.world = world;
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

        int y = this.sections.length << 4;

        while (y > 0) {
            short tile = this.sections[(y >> 4) - 1].tiles[tileX + tileZ * 16 + ((y % 16) << 8)];
            if (tile != 0) {
                return tile;
            }

            y--;
        }

        return 0;
    }

    public int getHeight(int tileX, int tileZ) {
        tileX = tileX % 16;
        tileZ = tileZ % 16;

        if (tileX < 0) {
            tileX = tileX + 16;
        }
        if (tileZ < 0) {
            tileZ = tileZ + 16;
        }

        int y = this.sections.length << 4;

        while (y > 0) {
            short tile = this.sections[(y >> 4) - 1].tiles[tileX + tileZ * 16 + ((y % 16) << 8)];
            if (tile != 0) {
                return y;
            }

            y--;
        }

        return 0;
    }

    public int getGenerationHeight(int tileX, int tileZ) {
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

    public short getTileAt(int x, int y, int z) {
        x = x % 16;
        z = z % 16;

        if (x < 0) {
            x = x + 16;
        }
        if (z < 0) {
            z = z + 16;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > this.sections.length * 16) {
            y = this.sections.length * 16;
        }

        return this.sections[(y - y & 0xF) >> 4].tiles[x + z * 16];
    }

    public void setTileAt(int x, int y, int z, short type) {
        x = x % 16;
        z = z % 16;

        if (x < 0) {
            x = x + 16;
        }
        if (z < 0) {
            z = z + 16;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > this.sections.length * 16) {
            y = this.sections.length * 16;
        }

        this.sections[(y - y & 0xF) >> 4].tiles[x + z * 16] = type;
    }

    public void populateHeight(short[] heights) {
        this.heights = heights;
    }

    public Section getSection(int section) {
        return null;
    }

    public int getNumSections() {
        return this.sections.length;
    }

    public void tick() {

    }

    public boolean hasBeenGenerated() {
        return this.isGenerated;
    }

    public void generate() {
        this.isGenerated = true;

        int[] ints = this.world.genLayer.getInts(this.chunkX * 16, this.chunkZ * 16, 16, 16);

        for (int i = 0; i < 256; i++) {
            this.sections[0].tiles[i] = (short) ints[i];
        }
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
