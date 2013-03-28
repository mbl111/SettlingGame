
package net.specialattack.settling.client.gui.element;

public abstract class GuiElement {

    public abstract void render(int mouseX, int mouseY);

    public abstract boolean mouseClicked(int mouseButton, int mouseX, int mouseY);

    public abstract boolean keyPressed(int keyCode, char character);

    public abstract boolean mouseScrolled(int wheel);

}
