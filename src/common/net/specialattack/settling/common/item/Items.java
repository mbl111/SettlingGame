
package net.specialattack.settling.common.item;

import net.specialattack.settling.common.Settling;

public class Items {
    public static final Item[] itemList = new Item[4096];

    public static ItemTile grass = new ItemTile(1, "Grass", "grass");
    public static ItemTile dirt = new ItemTile(2, "Dirt", "dirt");
    public static ItemTile water = new ItemTile(3, "Water", "water");
    public static ItemTile sand = new ItemTile(4, "Sand", "sand");

    static {
        Settling.getInstance().finishItems();
    }
}
