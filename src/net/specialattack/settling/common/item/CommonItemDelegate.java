
package net.specialattack.settling.common.item;

import net.specialattack.settling.common.IStitchedTexture;

public abstract class CommonItemDelegate {

    public abstract void createitem(Item item);

    public abstract void registerTextures(IStitchedTexture host);
}
