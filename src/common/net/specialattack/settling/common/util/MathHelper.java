
package net.specialattack.settling.common.util;

public final class MathHelper {

    private static float[] sinTable = new float[65536];

    static {
        for (int i = 0; i < 65536; ++i) {
            sinTable[i] = (float) Math.sin((double) i * Math.PI * 2.0D / 65536.0D);
        }
    }

    /*
     * Angles are 0 = 0 degrees, 1 = 90 degrees, 2 = 180 degrees, 3 = 270
     * degrees
     */
    public static final float sin(float angle) {
        return sinTable[(int) (angle * 16384.0F) & 65535];
    }

    /*
     * Angles are 0 = 0 degrees, 1 = 90 degrees, 2 = 180 degrees, 3 = 270
     * degrees
     */
    public static final float cos(float angle) {
        return sinTable[(int) (angle * 16384.0F + 16384.0F) & 65535];
    }

    public static int max(int number1, int number2) {
        return number1 > number2 ? number1 : number2;
    }

    public static int min(int number1, int number2) {
        return number1 < number2 ? number1 : number2;
    }

    public static int abs(int number) {
        return number < 0 ? -number : number;
    }

    public static float abs(float number) {
        return number < 0.0F ? -number : number;
    }

    public static double abs(double number) {
        return number < 0.0D ? -number : number;
    }

    public static float lerp(float origin, float target, int steps, int maxSteps) {
        return origin + (target - origin) * (float) steps / (float) maxSteps;
    }

    public static double lerp(double origin, double target, int steps, int maxSteps) {
        return origin + (target - origin) * (double) steps / (double) maxSteps;
    }

}
