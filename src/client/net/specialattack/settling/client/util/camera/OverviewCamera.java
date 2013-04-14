
package net.specialattack.settling.client.util.camera;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.common.util.Location;
import net.specialattack.settling.common.world.World;

import org.lwjgl.opengl.GL11;

public class OverviewCamera implements ICamera {

    private Location location;
    private Location prevLocation;

    public OverviewCamera() {
        this.location = new Location(0.0F, 60.0F, 0.0F, 60.0F, 45.0F);
        this.prevLocation = new Location(0.0F, 60.0F, 0.0F, 60.0F, 45.0F);
    }

    @Override
    public void tick(World world, SettlingClient settling) {
        this.prevLocation.clone(this.location);
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
        return 45.0F;
    }

    @Override
    public Location getLocation() {
        // TODO Auto-generated method stub
        return this.location;
    }

}
