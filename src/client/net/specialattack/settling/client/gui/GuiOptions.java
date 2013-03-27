
package net.specialattack.settling.client.gui;

import net.specialattack.settling.client.SettlingClient;
import net.specialattack.settling.client.util.KeyBinding;
import net.specialattack.settling.client.util.Settings;
import net.specialattack.settling.common.lang.LanguageRegistry;

import org.lwjgl.input.Keyboard;

public class GuiOptions extends GuiScreen {

    private GuiButton buttonReturn;
    private GuiButtonKey[] buttons;

    private int currentKey = -1;

    private GuiScreen parent;

    public GuiOptions(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void keyPressed(int key) {
        if (this.currentKey == -1) {
            super.keyPressed(key);
        }
        else {
            KeyBinding binding = this.buttons[this.currentKey].key;
            binding.key = key;

            this.buttons[currentKey].label = LanguageRegistry.translate(binding.id) + ": " + Keyboard.getKeyName(binding.key);

            this.buttons[currentKey].changing = false;
            this.currentKey = -1;

            Settings.saveSettings();
        }
    }

    @Override
    public void onResize(int newWidth, int newHeight) {

    }

    @Override
    public void onInit() {
        buttons = new GuiButtonKey[Settings.keys.size()];

        int i = 0;

        for (i = 0; i < buttons.length; i++) {
            KeyBinding binding = Settings.keys.get(i);

            buttons[i] = new GuiButtonKey(LanguageRegistry.translate(binding.id) + ": " + Keyboard.getKeyName(binding.key), width / 2 - 310 + 320 * (i % 2), 50 + (i / 2) * 60, 300, 50, binding, this);

            this.elements.add(buttons[i]);
        }

        i++;

        buttonReturn = new GuiButton(LanguageRegistry.translate("gui.return"), width / 2 - 150, 50 + ((i + 0) / 2) * 60, 300, 50, this);

        this.elements.add(buttonReturn);
    }

    @Override
    public void onRender(int mouseX, int mouseY) {}

    @Override
    protected void onClickAction(GuiElement element, int mouseX, int mouseZ, int mouseButton) {
        if (element == buttonReturn) {
            SettlingClient.instance.displayScreen(parent);
        }

        for (int i = 0; i < this.buttons.length; i++) {
            if (element == this.buttons[i]) {
                if (this.currentKey != -1) {
                    this.buttons[this.currentKey].changing = false;
                }

                this.currentKey = i;

                this.buttons[i].changing = true;

                break;
            }
        }
    }

    @Override
    protected void onKeyAction(GuiElement element, int key) {
    }

}
