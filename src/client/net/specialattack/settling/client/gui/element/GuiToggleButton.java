
package net.specialattack.settling.client.gui.element;

import net.specialattack.settling.client.gui.GuiHelper;
import net.specialattack.settling.client.gui.GuiScreen;
import net.specialattack.settling.client.texture.TextureRegistry;
import net.specialattack.settling.common.util.Table;

import org.lwjgl.opengl.GL11;

public class GuiToggleButton<T> extends GuiElement {

    public String label;
    public int posX;
    public int posY;
    public int width;
    public int height;
    public GuiScreen screen;
    public Table<Integer, String, T> possibleValues;
    public int selectedValue;
    public T selected;

    public boolean enabled = true;

    public GuiToggleButton(int posX, int posY, int width, int height, GuiScreen screen) {
        this.label = "";
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.screen = screen;
        this.possibleValues = new Table<Integer, String, T>();
        this.selectedValue = 0;
        this.selected = null;
    }

    public void addOption(String name, T value) {
        this.possibleValues.insert(this.possibleValues.size(), name, value);

        if (this.possibleValues.size() == 1) {
            this.label = name;
        }
    }

    public void setValue(String name) {
        if (this.possibleValues.containsValue1(name)) {
            this.selectedValue = this.possibleValues.getKey1(name);

            this.label = this.possibleValues.getValue(this.selectedValue).getValue1();
        }
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

        if (!this.enabled) {
            vAdd = 0.125F;
            color = 0x888888FF;
        }

        GuiHelper.drawTexturedRectangle(this.posX, this.posY, this.width, this.height, 0.0F, 0.0F + vAdd, 0.5F, 0.0625F + vAdd);

        float textTop = (float) this.posY + (float) this.height / 2.0F - this.screen.font.fontSize / 2.0F;
        float textLeft = (float) this.posX + (float) this.width / 2.0F - (float) this.screen.font.getStringWidth(this.label) / 2.0F;

        this.screen.font.renderStringWithShadow(this.label, (int) textLeft, (int) textTop, color);

    }

    @Override
    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY) {
        if (!this.enabled || (mouseButton != 0 && mouseButton != 1)) {
            return false;
        }

        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
            if (mouseButton == 0) {
                this.selectedValue++;
            }
            else {
                this.selectedValue--;
            }

            if (this.selectedValue >= this.possibleValues.size()) {
                this.selectedValue = 0;
            }
            if (this.selectedValue < 0) {
                this.selectedValue = this.possibleValues.size() - 1;
            }

            this.label = this.possibleValues.getValue(this.selectedValue).getValue1();
            this.selected = this.possibleValues.getValue(this.selectedValue).getValue2();

            return true;
        }

        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, char character) {
        return false;
    }

    @Override
    public boolean mouseScrolled(int dir) {
        return false;
    }

}
