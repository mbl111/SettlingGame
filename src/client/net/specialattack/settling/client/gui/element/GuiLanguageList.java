
package net.specialattack.settling.client.gui.element;

import java.util.ArrayList;
import java.util.List;

import net.specialattack.settling.client.gui.GuiHelper;
import net.specialattack.settling.common.lang.LanguageRegistry;

import org.lwjgl.opengl.GL11;

public class GuiLanguageList extends GuiElement {

    public int posX;
    public int posY;
    public int width;
    public int height;
    public GuiScreen screen;
    public int scrollBarHeight = 14 * 20;
    public int scrollBarWidth = 18;

    public boolean enabled = true;

    public int selectedIndex = -1;

    public List<String> elements;
    private int scroll = 0;

    public GuiLanguageList(int posX, int posY, int width, int height, GuiScreen screen) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.screen = screen;
        elements = new ArrayList<String>();
        selectedIndex = LanguageRegistry.getCurrentLanguageIndex();
    }

    @Override
    public void render(int mouseX, int mouseY) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        //        TextureRegistry.getTexture("/textures/gui/controls.png").bindTexture();
        //
        //        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //
        //        float vAdd = 0.0F;
        //        int color = 0xFFFFFFFF;
        //
        //        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
        //            vAdd = 0.0625F;
        //            color = 0xFFFF00FF;
        //        }
        //
        //        if (!enabled) {
        //            vAdd = 0.125F;
        //            color = 0x888888FF;
        //        }
        //
        //        GuiHelper.drawTexturedRectangle(posX, posY, width, height, 0.0F, 0.0F + vAdd, 0.5F, 0.0625F + vAdd);
        //
        //        float textTop = (float) this.posY + (float) this.height / 2.0F - this.screen.font.fontSize / 2.0F;
        //        float textLeft = (float) this.posX + (float) this.width / 2.0F - (float) this.screen.font.getStringWidth(label) / 2.0F;

        GuiHelper.renderRectangle(this.width - 22 + this.posX, this.posY - 2, 24, this.height, 0x444444FF);
        GuiHelper.renderRectangle(this.width - 20 + this.posX, this.posY, 20, this.height - 2, 0x666666FF);
        GuiHelper.renderRectangle(this.width - 19 + this.posX, this.posY + (this.height / elements.size()) * scroll, scrollBarWidth, scrollBarHeight, 0xAAAAAAFF);

        int lengthToRender = elements.size() >= scroll + 14 ? scroll + 14 : elements.size();
        for (int i = scroll; i < lengthToRender; i++) {
            String s = elements.get(i);
            if (s != null) {
                int color = 0xBBBBAAFF;
                if (i == selectedIndex) {
                    color = 0xFFFFFFFF;
                    this.screen.font.renderStringWithShadow(">", this.posX, (i - scroll) * 20 + this.posY, color);
                }
                this.screen.font.renderStringWithShadow(s, this.posX + 20, (i - scroll) * 20 + this.posY, color);

            }
        }
    }

    public void add(String string) {
        elements.add(string);
        if (elements.size() < 14) {
            scrollBarHeight = this.height - 4;
        }
        else {
            float percent = (14F / elements.size());
            scrollBarHeight = (int) (percent * (this.height - 4));
        }
    }

    @Override
    public boolean mouseClicked(int mouseButton, int mouseX, int mouseY) {
        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height) {
            if (mouseX < this.posX + this.width - 20) {
                int index = (mouseY - this.posY) / 20 + scroll;
                if (index > elements.size() - 1)
                    return false;
                selectedIndex = index;
            }
            else {
                //Scroll Bar
                int index = (mouseY - this.posY + scroll) / 20;
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseScrolled(int dir) {
        scroll += (dir / 120) * -1;
        if (scroll < 0)
            scroll = 0;

        int scrollMax = elements.size() - 14 <= 0 ? 0 : elements.size() - 14;

        if (scroll > scrollMax)
            scroll = scrollMax;
        return false;
    }
}
