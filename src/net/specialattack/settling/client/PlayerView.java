package net.specialattack.settling.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


public class PlayerView {

	public Location location;
	public float vSpeed = 4.0F;
	public float hSpeed = 10.0F;
	
	public PlayerView() {
		location = new Location(0, 0, 0, 0F, 90 + 45F);
	}
	
	public void tick() {
		float mouseDX = Mouse.getDX() * 0.8f * 0.16f;
		float mouseDY = Mouse.getDY() * 0.8f * 0.16f;
		if (location.yaw + mouseDX >= 360) {
			location.yaw = location.yaw + mouseDX - 360;
		} else if (location.yaw + mouseDX < 0) {
			location.yaw = 360 - location.yaw + mouseDX;
		} else {
			location.yaw += mouseDX;
		}
		if (location.pitch - mouseDY >= -89 && location.pitch - mouseDY <= 89) {
			location.pitch += -mouseDY;
		} else if (location.pitch - mouseDY < -89) {
			location.pitch = -89;
		} else if (location.pitch - mouseDY > 89) {
			location.pitch = 89;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			location.x += Math.sin(location.yaw * Math.PI / 180) * hSpeed;
			location.z += -Math.cos(location.yaw * Math.PI / 180) * hSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			location.x -= Math.sin(location.yaw * Math.PI / 180) * hSpeed;
			location.z -= -Math.cos(location.yaw * Math.PI / 180) * hSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			location.x += Math.sin((location.yaw - 90) * Math.PI / 180) * hSpeed;
			location.z += -Math.cos((location.yaw - 90) * Math.PI / 180) * hSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			location.x += Math.sin((location.yaw + 90) * Math.PI / 180) * hSpeed;
			location.z += -Math.cos((location.yaw + 90) * Math.PI / 180) * hSpeed;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			location.y += vSpeed;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			location.y -= vSpeed;
		}

	}

	public void lookThrough() {
		// roatate the pitch around the X axis
		GL11.glRotatef(location.pitch, 1.0f, 0.0f, 0.0f);
		// roatate the yaw around the Y axis
		GL11.glRotatef(location.yaw, 0.0f, 1.0f, 0.0f);
		// translate to the position vector's location
		GL11.glTranslatef(location.x, -location.y - 2.4f, location.z);
	}

}
