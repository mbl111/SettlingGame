
package net.specialattack.settling.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PlayerView {

    public Location location;
    public float vSpeed = 4.0F;
    public float hSpeed = 10.0F;

    public PlayerView() {
        this.location = new Location(0, 0, 0, 0F, 90 + 45F);
    }

    public void tick() {
        float mouseDX = Mouse.getDX() * 0.8f * 0.16f;
        float mouseDY = Mouse.getDY() * 0.8f * 0.16f;
        if (this.location.yaw + mouseDX >= 360) {
            this.location.yaw = this.location.yaw + mouseDX - 360;
        }
        else if (this.location.yaw + mouseDX < 0) {
            this.location.yaw = 360 - this.location.yaw + mouseDX;
        }
        else {
            this.location.yaw += mouseDX;
        }
        if (this.location.pitch - mouseDY >= -89 && this.location.pitch - mouseDY <= 89) {
            this.location.pitch += -mouseDY;
        }
        else if (this.location.pitch - mouseDY < -89) {
            this.location.pitch = -89;
        }
        else if (this.location.pitch - mouseDY > 89) {
            this.location.pitch = 89;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.location.x += Math.sin(this.location.yaw * Math.PI / 180) * this.hSpeed;
            this.location.z += -Math.cos(this.location.yaw * Math.PI / 180) * this.hSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.location.x -= Math.sin(this.location.yaw * Math.PI / 180) * this.hSpeed;
            this.location.z -= -Math.cos(this.location.yaw * Math.PI / 180) * this.hSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.location.x += Math.sin((this.location.yaw - 90) * Math.PI / 180) * this.hSpeed;
            this.location.z += -Math.cos((this.location.yaw - 90) * Math.PI / 180) * this.hSpeed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.location.x += Math.sin((this.location.yaw + 90) * Math.PI / 180) * this.hSpeed;
            this.location.z += -Math.cos((this.location.yaw + 90) * Math.PI / 180) * this.hSpeed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            this.location.y += this.vSpeed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            this.location.y -= this.vSpeed;
        }

    }

    public void lookThrough() {
        // roatate the pitch around the X axis
        GL11.glRotatef(this.location.pitch, 1.0f, 0.0f, 0.0f);
        // roatate the yaw around the Y axis
        GL11.glRotatef(this.location.yaw, 0.0f, 1.0f, 0.0f);
        // translate to the position vector's location
        GL11.glTranslatef(this.location.x, -this.location.y - 2.4f, this.location.z);
    }

}
