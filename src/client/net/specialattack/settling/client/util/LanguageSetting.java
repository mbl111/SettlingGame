
package net.specialattack.settling.client.util;

import net.specialattack.settling.common.lang.LanguageRegistry;

public class LanguageSetting implements ISetting {

    private String name;

    public LanguageSetting(String name) {
        this.name = name;
        Settings.settings.put("language.selected", this);
    }

    @Override
    public String getKey() {
        return "";
    }

    @Override
    public String getValue() {
        return name;
    }

    public int getIndex() {
        return LanguageRegistry.getIndexFromLangName(name);
    }

    public void loadValue(String obj) {
        this.name = obj;
        LanguageRegistry.loadLang(obj);
    }

    public void update() {

    }

    public void set(String lang) {
        this.name = lang;
    }

}
