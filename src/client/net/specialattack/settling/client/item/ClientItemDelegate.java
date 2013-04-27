
package net.specialattack.settling.client.item;

import java.awt.image.BufferedImage;

import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Item;
import net.specialattack.settling.common.texture.IStitchedTexture;

public class ClientItemDelegate extends CommonItemDelegate {

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
    public void registerTextures(IStitchedTexture host) {
        BufferedImage image = TextureRegistry.openResource(this.item.fulltextureName);

        host.loadTexture(image, this.item.textureName, image.getWidth(), image.getHeight());
    }

}
