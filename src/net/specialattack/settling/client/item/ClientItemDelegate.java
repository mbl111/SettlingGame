
package net.specialattack.settling.client.item;

import java.awt.image.BufferedImage;

import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.IStitchedTexture;
import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Item;

public class ClientItemDelegate extends CommonItemDelegate {

    private Item item;

    @Override
    public void createitem(Item item) {
        this.item = item;
    }

    @Override
    public void registerTextures(IStitchedTexture host) {
        BufferedImage image = TextureRegistry.openResource(this.item.textureName);

        host.loadTexture(image, this.item.textureName, image.getWidth(), image.getHeight());
    }

}
