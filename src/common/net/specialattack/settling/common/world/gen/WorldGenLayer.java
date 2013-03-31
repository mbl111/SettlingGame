
package net.specialattack.settling.common.world.gen;

public abstract class WorldGenLayer {

    protected WorldGenLayer parent;

    private long baseSeed;
    private long globalSeed;
    private long localSeed;

    public WorldGenLayer(long seed) {
        this.baseSeed = seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += this.baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += this.baseSeed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += this.baseSeed;
    }

    public abstract int[] getInts(int startX, int startZ, int width, int height);

    public void initGlobalSeed(long seed) {
        this.globalSeed = seed;

        if (this.parent != null) {
            this.parent.initGlobalSeed(seed);
        }

        this.globalSeed *= this.globalSeed * 6364136223846793005L + 1442695040888963407L;
        this.globalSeed += this.baseSeed;
        this.globalSeed *= this.globalSeed * 6364136223846793005L + 1442695040888963407L;
        this.globalSeed += this.baseSeed;
        this.globalSeed *= this.globalSeed * 6364136223846793005L + 1442695040888963407L;
        this.globalSeed += this.baseSeed;
    }

    public void initLocalSeed(long chunkX, long chunkZ) {
        this.localSeed = this.globalSeed;
        this.localSeed *= this.localSeed * 6364136223846793005L + 1442695040888963407L;
        this.localSeed += chunkX;
        this.localSeed *= this.localSeed * 6364136223846793005L + 1442695040888963407L;
        this.localSeed += chunkZ;
        this.localSeed *= this.localSeed * 6364136223846793005L + 1442695040888963407L;
        this.localSeed += chunkX;
        this.localSeed *= this.localSeed * 6364136223846793005L + 1442695040888963407L;
        this.localSeed += chunkZ;
    }

    public int nextInt(int max) {
        int result = (int) ((this.localSeed >> 24) % (long) max);

        if (result < 0) {
            result += max;
        }

        this.localSeed *= this.localSeed * 6364136223846793005L + 1442695040888963407L;
        this.localSeed += this.globalSeed;
        return result;
    }

    protected int pickRandom(int... ints) {
        return ints[this.nextInt(ints.length)];
    }

}
