
package net.specialattack.settling.common.util;

public class Location {

    public double x, y, z;
    public float pitch, yaw;

    public Location(double x, double y, double z, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void clone(Location loc) {
        this.x = loc.x;
        this.y = loc.y;
        this.z = loc.z;
        this.yaw = loc.yaw;
        this.pitch = loc.pitch;
    }

    public void add(Location loc) {
        this.x += loc.x;
        this.y += loc.y;
        this.z += loc.z;
        this.yaw += loc.yaw;
        this.pitch += loc.pitch;
    }

}
