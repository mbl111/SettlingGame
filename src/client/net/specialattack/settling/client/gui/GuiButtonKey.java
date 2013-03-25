
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.client.util.KeyBinding;

import org.lwjgl.opengl.GL11;

public class GuiButtonKey extends GuiElement {

    public String label;
    public int posX;
    public int posY;
    public int width;
    public int height;
    public GuiScreen screen;
    public KeyBinding key;
    public boolean changing = false;

    public boolean enabled = true;

    public GuiButtonKey(String label, int posX, int posY, int width, int height, KeyBinding key, GuiScreen screen) {
        this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.key = key;
        this.screen = screen;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        TextureRegistry.getTexture("/textures/gui/controls.png").bindTexture();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float vAdd = 0.0F;
        int color = 0xFFFFFFFF;

        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
            vAdd = 0.0625F;
            color = 0xFFFF00FF;
        }

        if (changing) {
            vAdd = 0.0625F;
            color = 0xFF880088;
        }

        if (!enabled) {
            vAdd = 0.125F;
            color = 0x888888FF;
        }

        GuiHelper.drawTexturedRectangle(posX, posY, width, height, 0.0F, 0.0F + vAdd, 0.5F, 0.0625F + vAdd);

        float textTop = (float) this.posY + (float) this.height / 2.0F - this.screen.font.fontSize / 2.0F;
        float textLeft = (float) this.posX + (float) this.width / 2.0F - (float) this.screen.font.getStringWidth(label) / 2.0F;

        this.screen.font.renderStringWithShadow(label, (int) textLeft, (int) textTop, color);
    }

    @Override
    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY) {
        if (!this.enabled || mouseButton != 0) {
            return false;
        }

        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
            return true;
        }

        return false;
    }
}
