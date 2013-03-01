
package net.specialattack.settling.client.world.gen;

import java.util.Random;

import net.specialattack.settling.common.world.gen.NoiseGenerator;
import net.specialattack.settling.common.world.gen.SimplexNoiseGenerator;

public class TestWorldGenerator {
    // http://freespace.virgin.net/hugo.elias/models/m_perlin.htm

    public NoiseGenerator noise1;
    public NoiseGenerator noise2;
    public int waterLevel = 62;

    public TestWorldGenerator(long seed) {
        Random ran = new Random(seed);
        this.noise1 = new SimplexNoiseGenerator(ran.nextLong());
        this.noise2 = new SimplexNoiseGenerator(ran.nextLong());
    }

    public short[] getHeights(int chunkX, int chunkZ) {
        short[] result = new short[256];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                result[x + z * 16] = (short) (((this.noise1.noise((x * 0.0625D + chunkX) * 0.5D, (z * 0.0625D + chunkZ) * 0.75D) * 4.0D) + (this.noise2.noise((x * 0.0625D + chunkX) * 0.5D, (z * 0.0625D + chunkZ) * 0.5D) * 4.0D)));
            }
        }

        return result;
    }
}
