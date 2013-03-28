
package net.specialattack.settling.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import net.specialattack.settling.common.Settling;

public final class Settings {

    public static HashMap<String, ISetting> settings = new HashMap<String, ISetting>();
    public static ArrayList<KeyBinding> keys = new ArrayList<KeyBinding>();
    public static KeyBinding forward = new KeyBinding(17, "movement.forward");
    public static KeyBinding back = new KeyBinding(31, "movement.back");
    public static KeyBinding left = new KeyBinding(30, "movement.left");
    public static KeyBinding right = new KeyBinding(32, "movement.right");
    public static KeyBinding sneak = new KeyBinding(29, "movement.sneak");
    public static KeyBinding jump = new KeyBinding(57, "movement.jump");
    public static KeyBinding sprint = new KeyBinding(42, "movement.sprint");
    public static LanguageSetting language = new LanguageSetting("en_US"); // language.current
    public static DisplayModeSetting displayMode = new DisplayModeSetting(); // display.mode
    public static BooleanSetting fullscreen = new BooleanSetting(false, "display.fullscreen");

    public static void loadSettings() {
        BufferedReader reader = null;

        try {
            File file = new File("./settings.conf");

            if (!file.exists()) {
                file.createNewFile();
            }

            reader = new BufferedReader(new FileReader(file));

            String line = "";

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] split = line.split("=", 2);
                    if (split.length == 2) {
                        if (settings.containsKey(split[0].trim())) {
                            settings.get(split[0].trim()).loadValue(split[1].trim());
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            Settling.log.log(Level.SEVERE, "Failed reading configuration file", e);
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {}
        }
    }

    public static void saveSettings() {
        BufferedWriter writer = null;

        try {
            File file = new File("./settings.conf");

            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(file));

            for (String key : settings.keySet()) {
                writer.write(key + "=" + settings.get(key).getValue() + "\r\n");
            }
        }
        catch (IOException e) {
            Settling.log.log(Level.SEVERE, "Failed saving configuration file", e);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {}
        }
    }

    public static void update() {
        for (String key : settings.keySet()) {
            settings.get(key).update();
        }
    }

}
