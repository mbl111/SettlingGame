
package net.specialattack.settling.server.item;

import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Item;
import net.specialattack.settling.common.texture.IStitchedTexture;

public class ServerItemDeleagte extends CommonItemDelegate {

    private Item item;

    @Override
    public void createitem(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void registerTextures(IStitchedTexture host) {}

}
