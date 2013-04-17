
package net.specialattack.settling.client.util.camera;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.common.util.Location;
import net.specialattack.settling.common.util.MathHelper;
import net.specialattack.settling.common.world.World;

import org.lwjgl.opengl.GL11;

public class LinearTransitionCamera implements ICamera {

    private Location location;
    private Location prevLocation;
    private ICamera origin;
    private ICamera target;
    private float FOV;
    private float prevFOV;;
    private int ticks;

    public LinearTransitionCamera(ICamera origin, ICamera target) {
        this.origin = origin;
        this.target = target;
        this.location = new Location(0, 0, 0);
        this.prevLocation = new Location(0, 0, 0);
        this.location.clone(origin.getLocation());
        this.prevLocation.clone(origin.getLocation());
        this.FOV = this.prevFOV = origin.getFOV(0.0F);
    }

    @Override
    public void tick(World world, SettlingClient settling) {
        this.prevLocation.clone(this.location);
        this.prevFOV = this.FOV;

        if (this.ticks > 40) {
            settling.camera = this.target;
            return;
        }

        this.FOV = MathHelper.lerp(this.origin.getFOV(0.0F), this.target.getFOV(0.0F), this.ticks, 40);
        this.location.x = MathHelper.lerp(this.origin.getLocation().x, this.target.getLocation().x, this.ticks, 40);
        this.location.y = MathHelper.lerp(this.origin.getLocation().y, this.target.getLocation().y, this.ticks, 40);
        this.location.z = MathHelper.lerp(this.origin.getLocation().z, this.target.getLocation().z, this.ticks, 40);
        this.location.pitch = MathHelper.lerp(this.origin.getLocation().pitch, this.target.getLocation().pitch, this.ticks, 40);
        if (this.target.getLocation().yaw > this.origin.getLocation().yaw) {
            if (this.target.getLocation().yaw - this.origin.getLocation().yaw > 180.0F) {
                this.location.yaw = MathHelper.lerp(this.origin.getLocation().yaw, this.target.getLocation().yaw - 360.0F, this.ticks, 40);
            }
            else {
                this.location.yaw = MathHelper.lerp(this.origin.getLocation().yaw, this.target.getLocation().yaw, this.ticks, 40);
            }
        }
        else {
            if (this.origin.getLocation().yaw - this.target.getLocation().yaw > 180.0F) {
                this.location.yaw = MathHelper.lerp(this.origin.getLocation().yaw - 360.0F, this.target.getLocation().yaw, this.ticks, 40);
            }
            else {
                this.location.yaw = MathHelper.lerp(this.origin.getLocation().yaw, this.target.getLocation().yaw, this.ticks, 40);
            }
        }

        this.ticks++;
    }

    @Override
    public void lookThrough(float partialTicks) {
        // roatate the pitch around the X axis
        float pitch = this.prevLocation.pitch + (this.location.pitch - this.prevLocation.pitch) * partialTicks;
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
        // roatate the yaw around the Y axis
        float yaw = this.prevLocation.yaw + (this.location.yaw - this.prevLocation.yaw) * partialTicks;
        GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        // translate to the position vector's location
        double x = this.prevLocation.x + (this.location.x - this.prevLocation.x) * partialTicks;
        double y = this.prevLocation.y + (this.location.y - this.prevLocation.y) * partialTicks;
        double z = this.prevLocation.z + (this.location.z - this.prevLocation.z) * partialTicks;
        GL11.glTranslated(x, -y - 2.4F, z);
    }

    @Override
    public float getFOV(float partialTicks) {
        return this.prevFOV + (this.FOV - this.prevFOV) * partialTicks;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Location getPrevLocation() {
        return this.prevLocation;
    }

}
