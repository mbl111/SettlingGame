
package net.specialattack.settling.common.world.gen;

public class WorldGenerator {
    // http://freespace.virgin.net/hugo.elias/models/m_perlin.htm

    public NoiseGenerator noiseGenerator;

    public WorldGenerator(long seed) {
        this.noiseGenerator = new SimplexNoiseGenerator(seed);
    }

    public short[] getHeights(int chunkX, int chunkZ) {
        short[] result = new short[256];

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                result[x + y * 16] = (short) (this.noiseGenerator.noise((x * 0.0625D + chunkX) * 0.5D, (y * 0.0625D + chunkZ) * 0.5D) * 4.0D + 5.0D);
            }
        }

        return result;
    }
}
