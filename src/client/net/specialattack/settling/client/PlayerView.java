
package net.specialattack.settling.client;

import net.specialattack.settling.client.util.Settings;
import net.specialattack.settling.common.util.Location;
import net.specialattack.settling.common.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PlayerView {

    public Location location;
    public Location prevLocation;
    public float vSpeed = 0.2F;
    public float hSpeed = 0.2F;

    public PlayerView() {
        this.location = new Location(0.0F, 0.0F, 0.0F, 0.0F, 90.0F);
        this.prevLocation = new Location(0.0F, 0.0F, 0.0F, 0.0F, 90.0F);
    }

    public void tick(World world) {
        this.prevLocation.clone(this.location);

        float mouseDX = Mouse.getDX() * 0.8F * 0.16F;
        float mouseDY = Mouse.getDY() * 0.8F * 0.16F;
        if (this.location.yaw + mouseDX >= 360) {
            this.location.yaw = this.location.yaw + mouseDX - 360.0F;
            this.prevLocation.yaw -= 360.0F;
        }
        else if (this.location.yaw + mouseDX < 0) {
            this.location.yaw = 360.0F - this.location.yaw + mouseDX;
            this.prevLocation.yaw += 360.0F;
        }
        else {
            this.location.yaw += mouseDX;
        }
        if (this.location.pitch - mouseDY >= -89.0F && this.location.pitch - mouseDY <= 89.0F) {
            this.location.pitch += -mouseDY;
        }
        else if (this.location.pitch - mouseDY < -89.0F) {
            this.location.pitch = -89.0F;
        }
        else if (this.location.pitch - mouseDY > 89.0F) {
            this.location.pitch = 89.0F;
        }

        float mod = 1.0F;

        if (Settings.sneak.isPressed()) {
            mod = 10.0F;
        }

        if (Settings.back.isPressed()) {
            this.location.x += Math.sin(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z += -Math.cos(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Settings.forward.isPressed()) {
            this.location.x -= Math.sin(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z -= -Math.cos(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Settings.right.isPressed()) {
            this.location.x += Math.sin((this.location.yaw - 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z += -Math.cos((this.location.yaw - 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Settings.left.isPressed()) {
            this.location.x += Math.sin((this.location.yaw + 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z += -Math.cos((this.location.yaw + 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
        }

        if (Settings.jump.isPressed()) {
            this.location.y += this.vSpeed * mod;
        }

        if (Settings.sprint.isPressed()) {
            this.location.y -= this.vSpeed * mod;
        }

        if (this.location.x > world.getMaxXBorder()) {
            this.location.x = world.getMaxXBorder();
        }
        if (this.location.z > world.getMaxZBorder()) {
            this.location.z = world.getMaxZBorder();
        }
        if (this.location.x < world.getMinXBorder()) {
            this.location.x = world.getMinXBorder();
        }
        if (this.location.z < world.getMinZBorder()) {
            this.location.z = world.getMinZBorder();
        }

    }

    public void lookThrough(float partialTicks) {
        // roatate the pitch around the X axis
        float pitch = this.prevLocation.pitch + (this.location.pitch - this.prevLocation.pitch) * partialTicks;
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
        // roatate the yaw around the Y axis
        float yaw = this.prevLocation.yaw + (this.location.yaw - this.prevLocation.yaw) * partialTicks;
        GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        // translate to the position vector's location
        float x = this.prevLocation.x + (this.location.x - this.prevLocation.x) * partialTicks;
        float y = this.prevLocation.y + (this.location.y - this.prevLocation.y) * partialTicks;
        float z = this.prevLocation.z + (this.location.z - this.prevLocation.z) * partialTicks;
        GL11.glTranslatef(x, -y - 2.4F, z);
    }

}
