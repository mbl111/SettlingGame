
package net.specialattack.settling.client.sound;

import java.applet.Applet;
import java.applet.AudioClip;

import net.specialattack.settling.client.SettlingClient;

public class Sound {

    public static Sound click = getClip("click.wav");

    private static Sound getClip(String name) {
        return new Sound(name);
    }

    public static void init(){}
    
    private AudioClip clip;
    private String name;

    public Sound(String name) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource("/sounds/" + name));
            this.name = name;
        }
        catch (Exception e) {
            SettlingClient.log.warning("Failed to load sound " + name);
        }
    }

    public void play() {
        if (clip == null)
            return;
        
        try {
            new Thread() {
                public void run() {
                    clip.play();
                }
            }.start();
        }
        catch (Throwable e) {
            SettlingClient.log.warning("Failed to play sound: " + name);
        }
    }

}
