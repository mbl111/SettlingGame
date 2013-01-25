
package net.specialattack.settling.common.item;

import net.specialattack.settling.common.Settling;

public class Items {
    public static final Item[] itemList = new Item[1024];

    public static ItemTile grass = new ItemTile(1, "Grass", "grass");
    public static ItemTile dirt = new ItemTile(2, "Dirt", "dirt");

    static {
        Settling.getInstance().finishItems();
    }
}
