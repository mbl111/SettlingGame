
package net.specialattack.settling.common.world.gen;

import net.specialattack.settling.common.util.ArrayCache;

public class WorldGenLayerIslands extends WorldGenLayer {

    public WorldGenLayerIslands(long seed) {
        super(seed);
    }

    @Override
    public int[] getInts(int startX, int startZ, int width, int height) {
        int[] result = ArrayCache.getInts(startX * startZ);

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                this.initLocalSeed((long) (startX + x), (long) (startZ + z));
                result[x + z * width] = this.nextInt(10) == 0 ? 1 : 3;
            }
        }

        if (startX > -width && startX <= 0 && startZ > -height && startZ <= 0) {
            result[-startX + -startZ * width] = 1;
        }

        return result;
    }

}
