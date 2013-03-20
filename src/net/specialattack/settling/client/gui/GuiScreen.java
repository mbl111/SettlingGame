
package net.specialattack.settling.client.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.rendering.FontRenderer;

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

        this.onRender(mouseX, mouseY);
    }

    public void mousePressed(int mouseButton, int mouseX, int mouseY) {
        for (GuiElement element : this.elements) {
            if (element.mouseClicked(mouseButton, mouseX, mouseY)) {
                this.onAction(element, mouseX, mouseY, mouseButton);
            }
        }
    }

    public void keyPressed(int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            SettlingClient.instance.displayScreen(null);
        }
    }

    public void drawBackground() {
        GuiHelper.renderRectangle(0, 0, width, height, 0x22222299);
    }

    protected abstract void onResize(int newWidth, int newHeight);

    protected abstract void onInit();

    protected abstract void onRender(int mouseX, int mouseY);

    protected abstract void onAction(GuiElement element, int mouseX, int mouseZ, int mouseButton);

}
