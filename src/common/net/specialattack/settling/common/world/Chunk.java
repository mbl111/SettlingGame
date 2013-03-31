
package net.specialattack.settling.common.world;

public class Chunk {
    public final int chunkX;
    public final int chunkZ;
    public final World world;
    private boolean isGenerated = false;
    private short[] tiles;
    private short[] colors;

    public Chunk(World world, int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.world = world;

        this.tiles = new short[256];
        this.colors = new short[256];
    }

    public short getTileAt(int x, int z) {
        x = x % 16;
        z = z % 16;

        if (x < 0) {
            x = x + 16;
        }
        if (z < 0) {
            z = z + 16;
        }

        return this.tiles[x + z * 16];
    }

    public void setTileAt(int x, int z, short type) {
        x = x % 16;
        z = z % 16;

        if (x < 0) {
            x = x + 16;
        }
        if (z < 0) {
            z = z + 16;
        }

        this.tiles[x + z * 16] = type;
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
            this.tiles[i] = (short) ints[i];
            this.colors[i] = (short) 0xFFFFFF;
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
