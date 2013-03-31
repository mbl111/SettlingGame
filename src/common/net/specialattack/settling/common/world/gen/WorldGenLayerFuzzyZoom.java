
package net.specialattack.settling.common.world.gen;

import net.specialattack.settling.common.util.ArrayCache;

public class WorldGenLayerFuzzyZoom extends WorldGenLayer {

    public WorldGenLayerFuzzyZoom(long seed, WorldGenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int startX, int startZ, int width, int height) {
        int extendedStartX = startX >> 1;
        int extendedStartZ = startZ >> 1;
        int extendedWidth = (width >> 1) + 3;
        int extendedHeight = (height >> 1) + 3;
        int[] parentInts = this.parent.getInts(extendedStartX, extendedStartZ, extendedWidth, extendedHeight);
        int[] ints = ArrayCache.getInts(extendedWidth * 2 * extendedHeight * 2);

        for (int z = 0; z < extendedHeight - 1; ++z) {
            int localIndex = (z << 1) * (extendedWidth << 1);
            int intTL = parentInts[0 + (z + 0) * extendedWidth];
            int intTR = parentInts[0 + (z + 1) * extendedWidth];

            for (int x = 0; x < extendedWidth - 1; ++x) {
                this.initLocalSeed((long) (x + extendedStartX << 1), (long) (z + extendedStartZ << 1));
                int intBL = parentInts[x + 1 + (z + 0) * extendedWidth];
                int intBR = parentInts[x + 1 + (z + 1) * extendedWidth];
                ints[localIndex] = intTL;
                ints[localIndex++ + (extendedWidth << 1)] = this.pickRandom(intTL, intTR);
                ints[localIndex] = this.pickRandom(intTL, intBL);
                ints[localIndex++ + (extendedWidth << 1)] = this.pickRandom(intTL, intBL, intTR, intBR);
                intTL = intBL;
                intTR = intBR;
            }
        }

        int[] result = ArrayCache.getInts(width * height);

        for (int i = 0; i < height; ++i) {
            System.arraycopy(ints, (i + (startZ & 1)) * (extendedWidth << 1) + (startX & 1), result, i * width, width);
        }

        return result;
    }

    public static WorldGenLayerFuzzyZoom zoom(long seed, WorldGenLayer genLayer, int amount) {

        for (int i = 0; i < amount; ++i) {
            genLayer = new WorldGenLayerFuzzyZoom(seed + (long) i, genLayer);
        }

        return (WorldGenLayerFuzzyZoom) genLayer;
    }
}
