package me.v0idpointer.ld49.sfx;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    public static Sound hit = new Sound("hit.wav");
    public static Sound fire = new Sound("fire.wav");
    public static Sound death = new Sound("death.wav");

    public static Sound defaultSound;

    private AudioClip clip;

    public Sound(String string) {
        try {
            this.clip = Applet.newAudioClip(this.getClass().getResource("/sounds/" + string));
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Sound initialization error: " + ex.getLocalizedMessage());
        }
    }

    public void play() {
        try {
            this.clip.play();
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Sound runtime error: " + ex.getLocalizedMessage());
        }
    }

}
