
package net.specialattack.settling.client.util;

import net.specialattack.settling.common.lang.LanguageRegistry;

public class LanguageSetting implements ISetting {

    private String name;

    public LanguageSetting(String name) {
        this.name = name;
        Settings.settings.put("language.current", this);
    }

    @Override
    public String getKey() {
        return "language.current";
    }

    @Override
    public String getValue() {
        return this.name;
    }

    public int getIndex() {
        return LanguageRegistry.getIndexFromLangName(this.name);
    }

    @Override
    public void loadValue(String obj) {
        this.name = obj;
        LanguageRegistry.loadLang(obj);
    }

    @Override
    public void update() {}

    public void set(String lang) {
        this.name = lang;
    }

}
