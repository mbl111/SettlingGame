
package net.specialattack.settling.common.world.gen;

import net.specialattack.settling.common.util.ArrayCache;

public class WorldGenLayerZoom extends WorldGenLayer {

    public WorldGenLayerZoom(long seed, WorldGenLayer parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int startX, int startZ, int width, int height) {
        int i1 = startX >> 1;
        int j1 = startZ >> 1;
        int k1 = (width >> 1) + 3;
        int l1 = (height >> 1) + 3;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = ArrayCache.getIntArray(k1 * 2 * l1 * 2);
        int i2 = k1 << 1;
        int j2;

        for (int k2 = 0; k2 < l1 - 1; ++k2) {
            j2 = k2 << 1;
            int l2 = j2 * i2;
            int i3 = aint[0 + (k2 + 0) * k1];
            int j3 = aint[0 + (k2 + 1) * k1];

            for (int k3 = 0; k3 < k1 - 1; ++k3) {
                this.initLocalSeed((long) (k3 + i1 << 1), (long) (k2 + j1 << 1));
                int l3 = aint[k3 + 1 + (k2 + 0) * k1];
                int i4 = aint[k3 + 1 + (k2 + 1) * k1];
                aint1[l2] = i3;
                aint1[l2++ + i2] = this.pickRandom(i3, j3);
                aint1[l2] = this.pickRandom(i3, l3);
                aint1[l2++ + i2] = this.pickMost(i3, l3, j3, i4);
                i3 = l3;
                j3 = i4;
            }
        }

        int[] aint2 = ArrayCache.getIntArray(width * height);

        for (j2 = 0; j2 < height; ++j2) {
            System.arraycopy(aint1, (j2 + (startZ & 1)) * (k1 << 1) + (startX & 1), aint2, j2 * width, width);
        }

        return aint2;
    }

    protected int pickMost(int int1, int int2, int int3, int int4) {
        if (int2 == int3 && int3 == int4) {
            return int2;
        }
        else if (int1 == int2 && int1 == int3) {
            return int1;
        }
        else if (int1 == int2 && int1 == int4) {
            return int1;
        }
        else if (int1 == int3 && int1 == int4) {
            return int1;
        }
        else if (int1 == int2 && int3 != int4) {
            return int1;
        }
        else if (int1 == int3 && int2 != int4) {
            return int1;
        }
        else if (int1 == int4 && int2 != int3) {
            return int1;
        }
        else if (int2 == int1 && int3 != int4) {
            return int2;
        }
        else if (int2 == int3 && int1 != int4) {
            return int2;
        }
        else if (int2 == int4 && int1 != int3) {
            return int2;
        }
        else if (int3 == int1 && int2 != int4) {
            return int3;
        }
        else if (int3 == int2 && int1 != int4) {
            return int3;
        }
        else if (int3 == int4 && int1 != int2) {
            return int3;
        }
        else if (int4 == int1 && int2 != int3) {
            return int3;
        }
        else if (int4 == int2 && int1 != int3) {
            return int3;
        }
        else if (int4 == int3 && int1 != int2) {
            return int3;
        }
        else {
            int rand = this.nextInt(4);
            switch (rand) {
            case 0:
                return int1;
            case 1:
                return int2;
            case 2:
                return int3;
            default:
                return int4;
            }
        }
    }

    public static WorldGenLayerZoom zoom(long seed, WorldGenLayer genLayer, int amount) {

        for (int i = 0; i < amount; ++i) {
            genLayer = new WorldGenLayerZoom(seed + (long) i, genLayer);
        }

        return (WorldGenLayerZoom) genLayer;
    }
}
