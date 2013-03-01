
package net.specialattack.settling.common.item;

import java.util.HashMap;

public class ItemStack {

    private int itemId;
    private int count;
    private short data;

    public HashMap<String, String> metaData = new HashMap<String, String>();

    public ItemStack(Item item) {
        this(item.identifier, 1);
    }

    public ItemStack(Item item, int count) {
        this(item.identifier, count);
    }

    public ItemStack(int id) {
        this(id, 1);
    }

    public ItemStack(int id, int count) {
        this.itemId = id;
        this.count = count;
    }

    public void setAmount(int count) {
        this.count = count;
    }

    public int getAmount() {
        return count;
    }

    public int getItemId() {
        return itemId;
    }

    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    public Item getItem() {
        return Items.itemList[this.itemId];
    }

    public String getItemName() {
        return this.getItem().getName();
    }

    public boolean canPlaceInWorld() {
        Item me = this.getItem();
        if (me instanceof ItemTile) {
            return ((ItemTile) me).canPlaceInWorld();
        }
        return false;
    }

    public void setDisplayName(String name) {
        metaData.put("display", name);
    }

    public boolean hasDisplayName() {
        return this.metaData.size() > 0 ? (metaData.containsKey("display")) : false;
    }

    public String getDisplayName() {
        if (this.hasDisplayName())
            return metaData.get("display");
        return this.getItemName();
    }

}
