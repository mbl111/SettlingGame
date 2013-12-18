
package net.specialattack.settling.common.util;

public class Vector {

    public double posX;
    public double posY;
    public double posZ;

    public Vector() {}

    public Vector(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void add(Vector other) {
        this.posX += other.posX;
        this.posY += other.posY;
        this.posZ += other.posZ;
    }

    public void subtract(Vector other) {
        this.posX -= other.posX;
        this.posY -= other.posY;
        this.posZ -= other.posZ;
    }

    public void multiply(double factor) {
        this.posX *= factor;
        this.posY *= factor;
        this.posZ *= factor;
    }

    public void devide(double factor) {
        this.posX /= factor;
        this.posY /= factor;
        this.posZ /= factor;
    }

    public double multiply(Vector other) {
        return this.posX * other.posX + this.posY * other.posY + this.posZ * other.posZ;
    }

    @Override
    public Vector clone() {
        Vector result = new Vector(this.posX, this.posY, this.posZ);

        return result;
    }

}
