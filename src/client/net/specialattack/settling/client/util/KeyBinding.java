
package net.specialattack.settling.client.util;

import org.lwjgl.input.Keyboard;

public class KeyBinding {

    public int key;
    public boolean pressed = false;
    public boolean aknowledged = false;

    public static KeyBinding escape = new KeyBinding(Keyboard.KEY_ESCAPE);

    public KeyBinding(int key) {
        this.key = key;
    }

    public boolean isTapped() {
        if (!aknowledged) {
            aknowledged = true;

            return true;
        }

        return false;
    }

    public boolean isPressed() {
        return Keyboard.isKeyDown(key);
    }

    public void update() {
        if (Keyboard.isKeyDown(key)) {
            if (!pressed) {
                pressed = true;
                aknowledged = false;
            }
        }
        else if (pressed) {
            pressed = false;
            aknowledged = true;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + key;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        KeyBinding other = (KeyBinding) obj;
        if (key != other.key) {
            return false;
        }
        return true;
    }

}
