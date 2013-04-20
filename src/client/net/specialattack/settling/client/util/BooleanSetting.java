
package net.specialattack.settling.client.util;

public class BooleanSetting implements ISetting {

    private boolean value;
    private String key;

    public BooleanSetting(boolean value, String key) {
        this.key = key;
        this.value = value;
        Settings.settings.put(key, this);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return "" + this.value;
    }

    @Override
    public void loadValue(String obj) {
        this.value = Boolean.parseBoolean(obj);
    }

    @Override
    public void update() {}

    public boolean getState() {
        return this.value;
    }

    public boolean toggleState() {
        this.value = !this.value;
        return this.value;
    }

    public void setState(boolean value) {
        this.value = value;
    }

}
