
package net.specialattack.settling.common.world;

public class Chunk {

    public int chunkX;
    public int chunkZ;
    public Section section;
    private boolean isGenerated = false;
    private boolean[] isLand;

    public Chunk(Section section, int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.section = section;

        this.isLand = new boolean[256];
    }

    public boolean isLandAt(int x, int z) {
        if (x < 0 || x > 15) {
            return false;
        }
        if (z < 0 || z > 15) {
            return false;
        }

        return this.isLand[x + z * 16];
    }

    public boolean hasBeenGenerated() {
        return this.isGenerated;
    }

    public void generate() {
        this.isGenerated = true;

        int[] ints = this.section.world.genLayer.getInts(this.chunkX * 16, this.chunkZ * 16, 16, 16);

        for (int i = 0; i < 256; i++) {
            this.isLand[i] = ints[i] > 0;
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
