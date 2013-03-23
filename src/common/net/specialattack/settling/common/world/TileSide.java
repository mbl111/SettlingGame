
package net.specialattack.settling.common.world;

import net.specialattack.settling.common.util.Location;

public enum TileSide {

    NORTH(1, 0, 0), SOUTH(-1, 0, 0), EAST(0, 0, 1), WEST(0, 0, -1), TOP(0, 1, 0), BOTTOM(0, -1, 0);

    TileSide(int x, int y, int z) {
        offset = new Location(x, y, z);
    }

    public Location offset;

}
