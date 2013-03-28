
package net.specialattack.settling.client.gui.element;

import java.util.ArrayList;

import net.specialattack.settling.client.gui.GuiHelper;
import net.specialattack.settling.client.gui.GuiScreen;
import net.specialattack.settling.common.lang.LanguageRegistry;

import org.lwjgl.opengl.GL11;

public class GuiList extends GuiElement {

    public int posX;
    public int posY;
    public int width;
    public int height;
    public GuiScreen screen;
    public int scrollBarHeight;

    public boolean enabled = true;

    public int selectedIndex = -1;

    public ArrayList<String> elements;
    private int scroll = 0;

    public GuiList(int posX, int posY, int width, int height, GuiScreen screen) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.screen = screen;
        this.elements = new ArrayList<String>();
        this.selectedIndex = LanguageRegistry.getCurrentLanguageIndex();
    }

    @Override
    public void render(int mouseX, int mouseY) {

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GuiHelper.renderRectangle(this.posX + this.width - 22, this.posY, 24, this.height, 0x444444FF);
        GuiHelper.renderRectangle(this.width - 20 + this.posX, this.posY + 2, 20, this.height - 4, 0x666666FF);
        GuiHelper.renderRectangle(this.width - 19 + this.posX, this.posY + 2 + (this.height / this.elements.size()) * this.scroll, 18, this.scrollBarHeight, 0xAAAAAAFF);

        int lengthToRender = this.elements.size() >= this.scroll + this.height / 20 ? this.scroll + this.height / 20 : this.elements.size();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        for (int i = this.scroll; i < lengthToRender; i++) {
            String s = this.elements.get(i);
            if (s != null) {
                int color = 0xBBBBAAFF;
                if (i == this.selectedIndex) {
                    color = 0xFFFFFFFF;
                    this.screen.font.renderStringWithShadow(">", this.posX, (i - this.scroll) * 20 + this.posY, color);
                }
                this.screen.font.renderStringWithShadow(s, this.posX + 20, (i - this.scroll) * 20 + this.posY, color);

            }
        }
    }

    public void add(String string) {
        this.elements.add(string);
        reCalculate();
    }

    public void reCalculate() {
        if (this.elements.size() <= this.height / 20) {
            this.scrollBarHeight = this.height - 4;
        }
        else {
            float percent = ((float) this.height / 20.0F / this.elements.size());
            this.scrollBarHeight = (int) (percent * (this.height - 4));
        }
    }

    @Override
    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY) {
        if (!this.enabled || mouseButton != 0) {
            return false;
        }

        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
            if (mouseX < this.posX + this.width - 20) {
                int index = (mouseY - this.posY) / 20 + this.scroll;
                if (index > this.elements.size() - 1) {
                    return false;
                }
                this.selectedIndex = index;
            }
            else {
                // Scroll Bar
                // int index = (mouseY - this.posY + this.scroll) / 20;
            }
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
        this.scroll += (dir / 120) * -1;
        if (this.scroll < 0) {
            this.scroll = 0;
        }

        int scrollMax = this.elements.size() - 14 <= 0 ? 0 : this.elements.size() - 14;

        if (this.scroll > scrollMax) {
            this.scroll = scrollMax;
        }
        return false;
    }
}
