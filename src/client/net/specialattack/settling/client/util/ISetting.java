
package net.specialattack.settling.client.util;

public interface ISetting {

    public abstract String getKey();

    public abstract String getValue();

    public abstract void loadValue(String obj);

    public abstract void update();

}
