
package net.specialattack.settling.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PlayerView {

    public Location location;
    public Location prevLocation;
    public float vSpeed = 4.0F;
    public float hSpeed = 10.0F;

    public PlayerView() {
        this.location = new Location(0.0F, 0.0F, 0.0F, 0.0F, 90.0F);
        this.prevLocation = new Location(0.0F, 0.0F, 0.0F, 0.0F, 90.0F);
    }

    public void tick() {
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

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            mod = 10.0F;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.location.x += Math.sin(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z += -Math.cos(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.location.x -= Math.sin(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z -= -Math.cos(this.location.yaw * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.location.x += Math.sin((this.location.yaw - 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z += -Math.cos((this.location.yaw - 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.location.x += Math.sin((this.location.yaw + 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
            this.location.z += -Math.cos((this.location.yaw + 90.0F) * Math.PI / 180.0F) * this.hSpeed * mod;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            this.location.y += this.vSpeed * mod;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            this.location.y -= this.vSpeed * mod;
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
