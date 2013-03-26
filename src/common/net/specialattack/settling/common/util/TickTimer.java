
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

    public TickTimer(float tps) {
        this.tps = tps;

        this.lastNanoTimeSync = getSystemTime();
        this.lastSysTimeSync = System.nanoTime() / 1000000L;
    }

    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    public void update() {
        long var1 = getSystemTime();
        long var3 = var1 - this.lastSysTimeSync;
        long var5 = System.nanoTime() / 1000000L;
        double var7 = (double) var5 / 1000.0D;

        if (var3 <= 1000L && var3 >= 0L) {
            this.timeCounter += var3;
            this.totalTicks += var3;
            if (this.timeCounter > 1000L) {
                long var9 = var5 - this.lastNanoTimeSync;
                double var11 = (double) this.timeCounter / (double) var9;
                this.syncAdjustment += (var11 - this.syncAdjustment) * 0.20000000298023224D;
                this.lastNanoTimeSync = var5;
                this.timeCounter = 0L;
            }

            if (this.timeCounter < 0L) {
                this.lastNanoTimeSync = var5;
            }
        }
        else {
            this.lastNanoTime = var7;
        }

        this.lastSysTimeSync = var1;
        double var13 = (var7 - this.lastNanoTime) * this.syncAdjustment;
        this.lastNanoTime = var7;

        if (var13 < 0.0D) {
            var13 = 0.0D;
        }

        if (var13 > 1.0D) {
            var13 = 1.0D;
        }

        this.partialTicks = (float) ((double) this.partialTicks + var13 * (double) this.speed * (double) this.tps);
        this.remainingTicks = (int) this.partialTicks;
        this.partialTicks -= (float) this.remainingTicks;

        if (this.remainingTicks > 10) {
            this.remainingTicks = 10;
        }

        this.renderPartialTicks = this.partialTicks;
    }
}
