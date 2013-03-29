
package net.specialattack.settling.client.gui.element;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.GuiHelper;
import net.specialattack.settling.client.gui.GuiScreen;
import net.specialattack.settling.client.texture.TextureRegistry;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiTextbox extends GuiElement {

    public int posX;
    public int posY;
    public int width;
    public int height;
    public GuiScreen screen;
    public String text = "";
    private boolean active = false;

    public boolean enabled = true;

    public GuiTextbox(int posX, int posY, int width, int height, GuiScreen screen) {
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

        float vAdd = 0.125F;

        GuiHelper.drawTexturedRectangle(this.posX, this.posY, this.width, this.height, 0.0F, 0.0F + vAdd, 0.5F, 0.0625F + vAdd);

        this.screen.font.renderStringWithShadow(this.text, this.posX + 4, this.posY + (this.height / 2) - 8, 0xFFFFFFFF);
        if (this.active) {
            if (SettlingClient.instance.timer.totalTicks / 400 % 2 == 0) {
                // TODO: Add a | char to the text file
                // TODO: Add characters for anything other than the uppercase letters
                this.screen.font.renderStringWithShadow(":", this.posX + (this.text.length() == 0 ? 0 : this.screen.font.getStringWidth(this.text)) + 8, this.posY + (this.height / 2) - 8, 0xFFFFFFFF);
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

    @Override
    public boolean keyPressed(int keyCode, char character) {
        if (keyCode == Keyboard.KEY_BACK) {
            if (this.text.length() > 0) {
                this.text = this.text.substring(0, this.text.length() - 1);
            }
        }
        else {
            String txt = character > 32 ? Character.toString(character) : "";
            if (txt.length() == 1) {
                this.text += txt;
            }
        }

        return this.active;
    }

    @Override
    public boolean mouseScrolled(int dir) {
        return false;
    }
}
