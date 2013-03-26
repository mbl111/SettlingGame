
package net.specialattack.settling.client.rendering;

import net.specialattack.settling.client.texture.SubTexture;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.item.Item;
import net.specialattack.settling.common.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class ItemRenderer {

    private static int currentTexture = -1;

    public static void resetTexture() {
        currentTexture = -1;
    }

    public static void renderItemIntoGUI(ItemStack itemStack, FontRenderer fontRenderer, int x, int y) {
        // This code is just a blatant copy of tiles, it will only last until I start writing item renderers

        Item item = itemStack.getItem();
        int amount = itemStack.getAmount() > 999 ? 999 : itemStack.getAmount();

        SubTexture texture = TextureRegistry.getSubTexture(item.textureName);

        if (currentTexture != texture.getParent().getTextureId()) {
            currentTexture = texture.bindTexture();
        }

        float startU = 1.0F * (float) texture.getStartU() / (float) texture.getParent().getWidth();
        float startV = 1.0F * (float) texture.getStartV() / (float) texture.getParent().getHeight();
        float endU = 1.0F * (float) texture.getEndU() / (float) texture.getParent().getWidth();
        float endV = 1.0F * (float) texture.getEndV() / (float) texture.getParent().getHeight();

        float startX = (float) x;
        float startY = (float) y;
        float endX = startX + 32.0F;
        float endY = startY + 32.0F;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(startU, startV);
        GL11.glVertex2f(startX, startY);

        GL11.glTexCoord2f(endU, startV);
        GL11.glVertex2f(endX, startY);

        GL11.glTexCoord2f(endU, endV);
        GL11.glVertex2f(endX, endY);

        GL11.glTexCoord2f(startU, endV);
        GL11.glVertex2f(startX, endY);

        GL11.glEnd();

        int textXOffs = -fontRenderer.getStringWidth(amount + "");
        fontRenderer.renderString(amount + "", (int) endX + textXOffs, (int) endY - 16, 0xFFFFFFFF);
    }

}
