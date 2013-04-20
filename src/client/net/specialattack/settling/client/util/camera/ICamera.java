
package net.specialattack.settling.client.util.camera;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.common.util.Location;
import net.specialattack.settling.common.world.World;

public interface ICamera {

    public abstract void tick(World world, SettlingClient settling);

    public abstract void lookThrough(float partialTicks);

    public abstract float getFOV(float partialTicks);

    public abstract Location getLocation();

    public abstract Location getPrevLocation();

}
