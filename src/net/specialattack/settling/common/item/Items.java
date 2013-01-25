package net.specialattack.settling.common.item;

import net.specialattack.settling.common.Settling;

public class Items {
    public static final Item[] itemList = new Item[1024];
    
    public static ItemTile grass = new ItemTile(1, "Grass", "grass");
    
    static {
        Settling.getInstance().finishItems();
    }
}
