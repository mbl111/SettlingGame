
package net.specialattack.settling.client.rendering;

import org.lwjgl.opengl.GL11;

import net.specialattack.settling.client.texture.SubTexture;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.item.ItemTile;

public class TileRenderer {
    public static void renderTileFloor(ItemTile item, int posX, int posY, int posZ) {
        SubTexture texture = TextureRegistry.getSubTexture(item.textureName);

        texture.bindTexture();

        GL11.glBegin(GL11.GL_QUADS);

        float startU = 1.0F * (float) texture.getStartU() / (float) texture.getParent().getWidth();
        float startV = 1.0F * (float) texture.getStartV() / (float) texture.getParent().getHeight();
        float endU = 1.0F * (float) texture.getEndU() / (float) texture.getParent().getWidth();
        float endV = 1.0F * (float) texture.getEndV() / (float) texture.getParent().getHeight();

        float startX = (float) posX * 50.0F;
        float startY = (float) posY * 50.0F;
        float startZ = (float) posZ * 50.0F;
        float endX = startX + 50.0F;
        float endZ = startZ + 50.0F;

        GL11.glTexCoord2f(startU, startV);
        //GL11.glVertex3f(25.0F, 0.0F, 0.0F);
        GL11.glVertex3f(startX, startY, startZ);

        GL11.glTexCoord2f(endU, startV);
        //GL11.glVertex3f(50.0F, 12.5F, 0.0F);
        GL11.glVertex3f(endX, startY, startZ);

        GL11.glTexCoord2f(endU, endV);
        //GL11.glVertex3f(25.5F, 25.2F, 0.0F); // 25.2F to prevent borders
        GL11.glVertex3f(endX, startY, endZ);

        GL11.glTexCoord2f(startU, endV);
        //GL11.glVertex3f(0.0F, 12.5F, 0.0F);
        GL11.glVertex3f(startX, startY, endZ);

        GL11.glEnd();
    }
}
