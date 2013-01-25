
package net.specialattack.settling.common.item;

public class ItemTile extends Item {

    public ItemTile(int identifier, String name, String textureName) {
        super(identifier, name, textureName);
        this.textureName = "/textures/tiles/" + textureName + ".png";
    }

}
