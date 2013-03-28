
package net.specialattack.settling.client.gui;

import java.util.ArrayList;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.gui.element.GuiElement;
import net.specialattack.settling.client.rendering.FontRenderer;
import net.specialattack.settling.client.util.KeyBinding;

import org.lwjgl.opengl.GL11;

public abstract class GuiScreen {

    public int width;
    public int height;
    public FontRenderer font;
    protected ArrayList<GuiElement> elements;

    public GuiScreen() {
        this.font = new FontRenderer();
        this.elements = new ArrayList<GuiElement>();
    }

    public GuiScreen(int width, int height) {
        this();
        this.width = width;
        this.height = height;
    }

    public void resize(int newWidth, int newHeight) {
        this.onResize(newWidth, newHeight);

        this.width = newWidth;
        this.height = newHeight;
    }

    public void initialize(int width, int height) {
        this.elements.clear();

        this.width = width;
        this.height = height;

        this.onInit();
    }

    public void render(int mouseX, int mouseY) {
        this.drawBackground();

        for (GuiElement element : this.elements) {
            element.render(mouseX, mouseY);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.onRender(mouseX, mouseY);
    }

    public void mousePressed(int mouseButton, int mouseX, int mouseY) {
        for (GuiElement element : this.elements) {
            if (element.mouseClicked(mouseButton, mouseX, mouseY)) {
                this.onClickAction(element, mouseX, mouseY, mouseButton);
            }
        }
    }

    public void keyPressed(int key, char character) {
        if (KeyBinding.escape.isTapped()) {
            SettlingClient.instance.displayScreen(null);
        }

        for (GuiElement element : this.elements) {
            if (element.keyPressed(key, character)) {
                this.onKeyAction(element, key);
            }
        }

    }

    public void mouseScrolled(int wheel) {
        for (GuiElement element : this.elements) {
            if (element.mouseScrolled(wheel)) {
                this.onMouseScrolled(wheel);
            }
        }

    }

    public void drawBackground() {
        GuiHelper.renderRectangle(0, 0, this.width, this.height, 0x22222299);
    }

    protected abstract void onResize(int newWidth, int newHeight);

    protected abstract void onInit();

    protected abstract void onRender(int mouseX, int mouseY);

    protected abstract void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton);

    //Can just be ignored.. Perhaps if there is a hotkey pressed for a button or something. Tends to just be used with a textbox
    protected abstract void onKeyAction(GuiElement element, int key);

    protected abstract void onMouseScrolled(int wheel);

}
