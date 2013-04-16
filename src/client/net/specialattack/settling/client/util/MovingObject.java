
package net.specialattack.settling.client.util;

import net.specialattack.settling.common.util.MathHelper;

public class MovingObject {

    public double posX;
    public double posY;
    public double posZ;
    public float motionX;
    public float motionY;
    public float motionZ;
    public float drag;
    public boolean limited;
    public double limitX1;
    public double limitY1;
    public double limitZ1;
    public double limitX2;
    public double limitY2;
    public double limitZ2;

    public MovingObject(double x, double y, double z, float drag) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.motionX = 0.0F;
        this.motionY = 0.0F;
        this.motionZ = 0.0F;
        this.drag = drag;
        this.limited = false;
        this.limitX1 = 0.0D;
        this.limitY1 = 0.0D;
        this.limitZ1 = 0.0D;
        this.limitX2 = 0.0D;
        this.limitY2 = 0.0D;
        this.limitZ2 = 0.0D;
    }

    public MovingObject(double x, double y, double z, float drag, double limitX1, double limitY1, double limitZ1, double limitX2, double limitY2, double limitZ2) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.motionX = 0.0F;
        this.motionY = 0.0F;
        this.motionZ = 0.0F;
        this.drag = drag;
        this.limited = true;
        this.limitX1 = limitX1;
        this.limitY1 = limitY1;
        this.limitZ1 = limitZ1;
        this.limitX2 = limitX2;
        this.limitY2 = limitY2;
        this.limitZ2 = limitZ2;
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

        if (this.limited) {
            if (this.posX < this.limitX1) {
                this.posX = this.limitX1;
            }
            if (this.posX > this.limitX2) {
                this.posX = this.limitX2;
            }
            if (this.posY < this.limitY1) {
                this.posY = this.limitY1;
            }
            if (this.posY > this.limitY2) {
                this.posY = this.limitY2;
            }
            if (this.posZ < this.limitZ1) {
                this.posZ = this.limitZ1;
            }
            if (this.posZ > this.limitZ2) {
                this.posZ = this.limitZ2;
            }
        }
    }

}
