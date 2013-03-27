
package net.specialattack.settling.client.gui;

public abstract class GuiElement {

    public abstract void render(int mouseX, int mouseY);

    public abstract boolean mouseClicked(int mouseButton, int mouseX, int mouseY);

    public abstract boolean keyPressed(int keyCode, char character);
    
}
