
package net.specialattack.settling.common.util;

@Deprecated
public class MovingObject {

    public double posX;
    public double posY;
    public double posZ;
    public float motionX;
    public float motionY;
    public float motionZ;
    public float drag;

    public MovingObject(double x, double y, double z, float drag) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.motionX = 0.0F;
        this.motionY = 0.0F;
        this.motionZ = 0.0F;
        this.drag = drag;
    }

    public void update() {
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        this.motionX /= this.drag;
        this.motionY /= this.drag;
        this.motionZ /= this.drag;

        if (MathHelper.abs(this.motionX) < 0.01F) {
            this.motionX = 0.0F;
        }
        if (MathHelper.abs(this.motionY) < 0.01F) {
            this.motionY = 0.0F;
        }
        if (MathHelper.abs(this.motionZ) < 0.01F) {
            this.motionZ = 0.0F;
        }
    }

}
