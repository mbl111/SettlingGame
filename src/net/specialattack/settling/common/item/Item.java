
package net.specialattack.settling.common.item;

import net.specialattack.settling.common.Settling;

public class Item {
    public final int identifier;
    public final String name;
    public String fulltextureName;
    public String textureName;
    public final CommonItemDelegate delegate;

    public Item(int identifier, String name, String textureName) {
        if (Items.itemList[identifier] != null) {
            throw new IllegalArgumentException("Slot " + identifier + " is already occupied by " + Items.itemList[identifier].getClass().getName());
        }

        Items.itemList[identifier] = this;

        this.identifier = identifier;
        this.name = name;
        this.fulltextureName = "/textures/items/" + textureName + ".png";
        this.textureName = textureName;

        this.delegate = Settling.getInstance().getItemDelegate();
        this.delegate.createitem(this);
    }

    public boolean canPlaceInWorld() {
        return false;
    }

}
