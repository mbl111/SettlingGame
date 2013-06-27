
package net.specialattack.settling.client.util.camera;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.util.Settings;
import net.specialattack.settling.common.util.Location;
import net.specialattack.settling.common.util.MathHelper;
import net.specialattack.settling.common.world.World;

import org.lwjgl.opengl.GL11;

public class OverviewCamera implements ICamera {

    private Location location;
    private Location prevLocation;
    private float hSpeed = 2.0F;
    private float drag = 1.5F;
    private float motionX;
    private float motionZ;
    private float motionY;
    private boolean setup;

    public OverviewCamera() {
        this.location = new Location(0.0F, 60.0F, 0.0F, 60.0F, 45.0F);
        this.prevLocation = new Location(0.0F, 60.0F, 0.0F, 60.0F, 45.0F);
        this.motionX = 0.0F;
        this.motionZ = 0.0F;
        this.motionY = 0.0F;
        this.setup = false;
    }

    @Override
    public void tick(World world, SettlingClient settling) {
        if (!this.setup) {
            this.setup = true;
            this.location.y = world.getChunkAtTile((int) this.location.x, (int) this.location.z, false).getHighestBlock((int) this.location.x, (int) this.location.z) + 50.0F;
        }

        this.prevLocation.clone(this.location);

        float mod = 1.0F;

        if (Settings.sprint.isPressed()) {
            mod = 3.0F;
        }

        if (Settings.back.isPressed()) {
            this.motionX += Math.sin(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
            this.motionZ += -Math.cos(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Settings.forward.isPressed()) {
            this.motionX -= Math.sin(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
            this.motionZ -= -Math.cos(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Settings.right.isPressed()) {
            this.motionX += Math.sin((this.location.yaw - 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
            this.motionZ += -Math.cos((this.location.yaw - 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Settings.left.isPressed()) {
            this.motionX += Math.sin((this.location.yaw + 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
            this.motionZ += -Math.cos((this.location.yaw + 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
        }

        if (this.location.x >= world.getMaxXBorder()) {
            this.location.x = world.getMaxXBorder() - 1;
        }
        if (this.location.z >= world.getMaxZBorder()) {
            this.location.z = world.getMaxZBorder() - 1;
        }
        if (this.location.x < world.getMinXBorder()) {
            this.location.x = world.getMinXBorder();
        }
        if (this.location.z < world.getMinZBorder()) {
            this.location.z = world.getMinZBorder();
        }
        
        float highest = world.getChunkAtTile((int) this.location.x, (int) this.location.z, false).getHeight((int) this.location.x, (int) this.location.z) + 50;

        if (this.location.y < highest) {
            this.motionY = (highest - (float) this.location.y) / 3.0F;
        }
        else if (this.location.y > highest) {
            this.motionY = -((float) this.location.y - highest) / 3.0F;
        }
        else {
            this.motionY = 0.0F;
        }

        if (MathHelper.abs(this.motionY) <= 0.01F) {
            this.location.y = highest;
        }

        this.location.x += this.motionX;
        this.location.y += this.motionY;
        this.location.z += this.motionZ;

        this.motionX /= this.drag;
        this.motionZ /= this.drag;

        if (MathHelper.abs(this.motionX) < 0.01F) {
            this.motionX = 0.0F;
        }
        if (MathHelper.abs(this.motionZ) < 0.01F) {
            this.motionZ = 0.0F;
        }
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
        return 60.0F;
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
