
package net.specialattack.settling.common.item;

import net.specialattack.settling.common.Settling;

public class Item {
    public final int identifier;
    public final String name;
    public String textureName;
    public final CommonItemDelegate delegate;

    public Item(int identifier, String name, String textureName) {
        this.identifier = identifier;
        this.name = name;
        this.textureName = "/textures/items/" + textureName + ".png";

        this.delegate = Settling.getInstance().getItemDelegate();
    }

    public boolean canPlaceInWorld() {
        return false;
    }

}
