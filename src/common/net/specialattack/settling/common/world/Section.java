
package net.specialattack.settling.common.world;

public class Section {

    private Chunk[] chunks;
    public World world;
    public int sectionX;
    public int sectionZ;

    public Section(World world, int sectionX, int sectionZ) {
        this.world = world;
        this.sectionX = sectionX;
        this.sectionZ = sectionZ;

        this.chunks = new Chunk[256];
    }

    public Chunk getChunk(int chunkX, int chunkZ, boolean generateIfMissing) {
        if (chunkX < 0 || chunkX > 15 || chunkZ < 0 || chunkZ > 15) {
            return null;
        }
        Chunk chunk = this.chunks[chunkX + chunkZ * 16];
        if (chunk == null && generateIfMissing) {
            chunk = this.chunks[chunkX + chunkZ * 16] = new Chunk(this, chunkX + sectionX * 16, chunkZ + sectionZ * 16);
        }
        return chunk;
    }

    public void generateChunks() {
        for (int chunkX = 0; chunkX < 16; chunkX++) {
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                if (this.chunks[chunkX + chunkZ * 16] == null) {
                    Chunk chunk = this.chunks[chunkX + chunkZ * 16] = new Chunk(this, chunkX + sectionX * 16, chunkZ + sectionZ * 16);
                    chunk.generate();
                }
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.sectionX;
        result = prime * result + this.sectionZ;
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
        Section other = (Section) obj;
        if (this.sectionX != other.sectionX) {
            return false;
        }
        if (this.sectionZ != other.sectionZ) {
            return false;
        }
        return true;
    }

}
