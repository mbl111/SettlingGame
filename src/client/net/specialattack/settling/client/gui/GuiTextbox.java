
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.texture.TextureRegistry;

import org.lwjgl.opengl.GL11;

public class GuiTextbox extends GuiElement {

    //public String label;
    public int posX;
    public int posY;
    public int width;
    public int height;
    public GuiScreen screen;
    public String text = "";
    private boolean active = false;

    public boolean enabled = true;

    public GuiTextbox(int posX, int posY, int width, int height, GuiScreen screen) {
        //this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.screen = screen;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        TextureRegistry.getTexture("/textures/gui/controls.png").bindTexture();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float vAdd = 0.0F;
        int color = 0xFFFFFFFF;

        //        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
        //            vAdd = 0.0625F;
        //            color = 0xFFFF00FF;
        //        }

        vAdd = 0.125F * 1;
        color = 0x888888FF;

        GuiHelper.drawTexturedRectangle(posX, posY, width, height, 0.0F, 0.0F + vAdd, 0.5F, 0.0625F + vAdd);

        //this.screen.font.renderStringWithShadow(text, (int) textLeft, (int) textTop, color);

        if (active) {
            if (SettlingClient.instance.timer.totalTicks/400 % 2 == 0) {
                //This is where I need a total tick count or something... ticks % 10 < 5
                this.screen.font.renderStringWithShadow("a", this.posX + text.length() == 0 ? 0 : this.screen.font.getStringWidth(text), this.posY, 0xFFFFFFFF);
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY) {

        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
            this.active = true;
            return true;

        }
        this.active = false;
        return false;
    }
}
