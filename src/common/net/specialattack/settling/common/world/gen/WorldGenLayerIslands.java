
package net.specialattack.settling.common.world.gen;

import net.specialattack.settling.common.util.ArrayCache;

public class WorldGenLayerIslands extends WorldGenLayer {

    private int frequency;

    public WorldGenLayerIslands(long seed, int frequency) {
        super(seed);
        this.frequency = frequency;
    }

    @Override
    public int[] getInts(int startX, int startZ, int width, int height) {
        int[] result = ArrayCache.getIntArray(startX * startZ);

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                this.initLocalSeed((long) (startX + x), (long) (startZ + z));
                result[x + z * width] = this.nextInt(this.frequency) == 0 ? 1 : 0;
            }
        }

        if (startX > -width && startX <= 0 && startZ > -height && startZ <= 0) {
            result[-startX + -startZ * width] = 1;
        }

        return result;
    }

}
