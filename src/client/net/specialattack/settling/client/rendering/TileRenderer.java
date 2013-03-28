
package net.specialattack.settling.client.rendering;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.texture.SubTexture;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.item.ItemTile;

import org.lwjgl.opengl.GL11;

public class TileRenderer {

    private static int currentTexture = -1;

    public static void resetTexture() {
        currentTexture = -1;
    }

    public static void renderTileFloor(ItemTile item, int posX, int posY, int posZ) {
        SubTexture texture = TextureRegistry.getSubTexture(item.textureName);
        
        if (currentTexture != texture.getParent().getTextureId()) {
            currentTexture = texture.bindTexture();
        }

        GL11.glBegin(GL11.GL_QUADS);

        float startU = 1.0F * (float) texture.getStartU() / (float) texture.getParent().getWidth();
        float startV = 1.0F * (float) texture.getStartV() / (float) texture.getParent().getHeight();
        float endU = 1.0F * (float) texture.getEndU() / (float) texture.getParent().getWidth();
        float endV = 1.0F * (float) texture.getEndV() / (float) texture.getParent().getHeight();

        float startX = (float) posX;
        float startY = (float) posY;
        float startZ = (float) posZ;
        float endX = startX + 1.0F;
        float endZ = startZ + 1.0F;
        float endY = startY;

        if (!SettlingClient.firstPerson) {
            float temp = startY;
            startY = startZ;
            startZ = temp;

            temp = endY;
            endY = endZ;
            endZ = temp;
        }

        GL11.glTexCoord2f(startU, startV);
        //GL11.glVertex3f(25.0F, 0.0F, 0.0F);
        GL11.glVertex3f(startX, startY, startZ);

        GL11.glTexCoord2f(endU, startV);
        //GL11.glVertex3f(50.0F, 12.5F, 0.0F);
        GL11.glVertex3f(endX, startY, startZ);

        GL11.glTexCoord2f(endU, endV);
        //GL11.glVertex3f(25.5F, 25.2F, 0.0F); // 25.2F to prevent borders
        GL11.glVertex3f(endX, endY, endZ);

        GL11.glTexCoord2f(startU, endV);
        //GL11.glVertex3f(0.0F, 12.5F, 0.0F);
        GL11.glVertex3f(startX, endY, endZ);

        GL11.glEnd();
    }

    //teh north is X + 1
    public static void renderTileNorthFace(ItemTile item, int posX, int posY, int posZ) {
        SubTexture texture = TextureRegistry.getSubTexture(item.textureName);

        if (currentTexture != texture.getParent().getTextureId()) {
            currentTexture = texture.bindTexture();
        }

        GL11.glBegin(GL11.GL_QUADS);

        float startU = 1.0F * (float) texture.getStartU() / (float) texture.getParent().getWidth();
        float startV = 1.0F * (float) texture.getStartV() / (float) texture.getParent().getHeight();
        float endU = 1.0F * (float) texture.getEndU() / (float) texture.getParent().getWidth();
        float endV = 1.0F * (float) texture.getEndV() / (float) texture.getParent().getHeight();

        float startX = (float) posX + 1;
        float startY = (float) posY;
        float startZ = (float) posZ;
        float endX = startX;
        float endZ = startZ + 1.0F;
        float endY = startY + 1.0F;

        if (!SettlingClient.firstPerson) {
            float temp = startY;
            startY = startZ;
            startZ = temp;

            temp = endY;
            endY = endZ;
            endZ = temp;
        }

        GL11.glTexCoord2f(startU, startV);
        //GL11.glVertex3f(25.0F, 0.0F, 0.0F);
        GL11.glVertex3f(startX, startY, startZ);

        GL11.glTexCoord2f(endU, startV);
        //GL11.glVertex3f(50.0F, 12.5F, 0.0F);
        GL11.glVertex3f(startX, startY, endZ);

        GL11.glTexCoord2f(endU, endV);
        //GL11.glVertex3f(25.5F, 25.2F, 0.0F); // 25.2F to prevent borders
        GL11.glVertex3f(endX, endY, endZ);

        GL11.glTexCoord2f(startU, endV);
        //GL11.glVertex3f(0.0F, 12.5F, 0.0F);
        GL11.glVertex3f(startX, endY, startZ);

        GL11.glEnd();
    }
}
