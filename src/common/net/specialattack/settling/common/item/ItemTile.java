
package net.specialattack.settling.common.item;

public class ItemTile extends Item {

    public ItemTile(int identifier, String name, String textureName) {
        super(identifier, name, textureName);
        this.fulltextureName = "/textures/tiles/" + textureName + ".png";
    }

    @Override
    public boolean canPlaceInWorld() {
        return true;
    }

}
