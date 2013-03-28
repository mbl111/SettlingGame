
package net.specialattack.settling.client.util;

import java.util.logging.Level;

import net.specialattack.settling.common.Settling;

import org.lwjgl.input.Keyboard;

public class KeyBinding implements ISetting {

    public int key;
    public boolean pressed = false;
    public boolean aknowledged = false;
    public String id;

    public static KeyBinding escape = new KeyBinding(Keyboard.KEY_ESCAPE);

    public KeyBinding(int key) {
        this.key = key;
        this.id = "";
    }

    public KeyBinding(int key, String id) {
        this.key = key;
        this.id = id;
        Settings.settings.put(id, this);
        Settings.keys.add(this);
    }

    public boolean isTapped() {
        if (!this.aknowledged) {
            this.aknowledged = true;

            return true;
        }

        return false;
    }

    public boolean isPressed() {
        return Keyboard.isKeyDown(this.key);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.id.hashCode();
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        KeyBinding other = (KeyBinding) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public String getValue() {
        return "" + this.key;
    }

    @Override
    public void loadValue(String obj) {
        try {
            this.key = Integer.parseInt(obj);
        }
        catch (NumberFormatException e) {
            Settling.log.log(Level.WARNING, "Failed reading config setting '" + this.id + "'", e);
        }
    }

    @Override
    public void update() {
        if (Keyboard.isKeyDown(this.key)) {
            if (!this.pressed) {
                this.pressed = true;
                this.aknowledged = false;
            }
        }
        else if (this.pressed) {
            this.pressed = false;
            this.aknowledged = true;
        }
    }

}
