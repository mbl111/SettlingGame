
package net.specialattack.settling.common.util;

import java.util.ArrayList;

public class ArrayCache {

    private static final int cacheSize = 256;
    private static int bigCacheSize = 256;

    private static ArrayList<int[]> unusedInts = new ArrayList<int[]>();
    private static ArrayList<int[]> usedInts = new ArrayList<int[]>();
    private static ArrayList<int[]> unusedBigInts = new ArrayList<int[]>();
    private static ArrayList<int[]> usedBigInts = new ArrayList<int[]>();

    private static final Object lock = new Object();

    public static int[] getInts(int size) {
        synchronized (lock) {
            if (size <= cacheSize) {
                if (!unusedInts.isEmpty()) {
                    int[] selected = unusedInts.remove(unusedInts.size() - 1);
                    usedInts.add(selected);
                    return selected;
                }
                else {
                    int[] selected = new int[cacheSize];
                    usedInts.add(selected);
                    return selected;
                }
            }
            else {
                if (size > bigCacheSize) {
                    bigCacheSize = size;
                    unusedBigInts.clear();
                    usedBigInts.clear();
                }
                if (!unusedBigInts.isEmpty()) {
                    int[] selected = unusedBigInts.remove(unusedBigInts.size() - 1);
                    usedBigInts.add(selected);
                    return selected;
                }
                else {
                    int[] selected = new int[bigCacheSize];
                    usedBigInts.add(selected);
                    return selected;
                }
            }
        }
    }

    public static void freeInts() {
        if (!unusedInts.isEmpty()) {
            unusedInts.clear();
        }
        if (!unusedBigInts.isEmpty()) {
            unusedBigInts.clear();
        }

        unusedInts.addAll(usedInts);
        unusedBigInts.addAll(usedBigInts);
        usedInts.clear();
        usedBigInts.clear();
    }

}
