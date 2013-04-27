
package net.specialattack.settling.common.util;

import org.lwjgl.Sys;

public class TickTimer {

    public float tps;
    public double lastNanoTime;
    public long lastSysTimeSync;
    public long lastNanoTimeSync;
    public int remainingTicks = 0;
    public float renderPartialTicks = 0.0F;
    public float partialTicks = 0.0F;
    public float speed = 1.0F;
    public double syncAdjustment = 1.0D;
    public long timeCounter;
    public long totalTicks;

    private static boolean noLWJGL = false;

    public TickTimer(float tps) {
        this.tps = tps;

        this.lastNanoTimeSync = getSystemTime();
        this.lastSysTimeSync = System.nanoTime() / 1000000L;
    }

    public static long getSystemTime() {
        if (noLWJGL) {
            return System.nanoTime() / 1000000L;
        }
        else {
            try {
                return Sys.getTime() * 1000L / Sys.getTimerResolution();
            }
            catch (UnsatisfiedLinkError ex) {
                noLWJGL = true;
                return getSystemTime();
            }
        }
    }

    public void update() {
        long sysTime = getSystemTime();
        long timeDifference = sysTime - this.lastSysTimeSync;
        long microTime = System.nanoTime() / 1000000L;
        double nanoTime = (double) microTime / 1000.0D;

        if (timeDifference <= 1000L && timeDifference >= 0L) {
            this.timeCounter += timeDifference;
            this.totalTicks += timeDifference;
            if (this.timeCounter > 1000L) {
                long var9 = microTime - this.lastNanoTimeSync;
                double var11 = (double) this.timeCounter / (double) var9;
                this.syncAdjustment += (var11 - this.syncAdjustment) * 0.20000000298023224D;
                this.lastNanoTimeSync = microTime;
                this.timeCounter = 0L;
            }

            if (this.timeCounter < 0L) {
                this.lastNanoTimeSync = microTime;
            }
        }
        else {
            this.lastNanoTime = nanoTime;
        }

        this.lastSysTimeSync = sysTime;
        double partials = (nanoTime - this.lastNanoTime) * this.syncAdjustment;
        this.lastNanoTime = nanoTime;

        if (partials < 0.0D) {
            partials = 0.0D;
        }

        if (partials > 1.0D) {
            partials = 1.0D;
        }

        this.partialTicks = (float) ((double) this.partialTicks + partials * (double) this.speed * (double) this.tps);
        this.remainingTicks = (int) this.partialTicks;
        this.partialTicks -= (float) this.remainingTicks;

        if (this.remainingTicks > 10) {
            this.remainingTicks = 10;
        }

        this.renderPartialTicks = this.partialTicks;
    }
}
