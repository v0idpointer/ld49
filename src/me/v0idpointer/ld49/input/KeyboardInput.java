package me.v0idpointer.ld49.input;

import me.v0idpointer.ld49.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends KeyAdapter {

    public static int KEY_FORWARD = KeyEvent.VK_W;
    public static int KEY_BACKWARD = KeyEvent.VK_S;
    public static int KEY_RIGHT = KeyEvent.VK_D;
    public static int KEY_LEFT = KeyEvent.VK_A;
    public static int KEY_QUICK_SWITCH = KeyEvent.VK_Q;
    public static int KEY_USE = KeyEvent.VK_F;
    public static int KEY_ATTACK = KeyEvent.VK_SPACE;
    public static int KEY_OPEN_MAP = KeyEvent.VK_M;
    public static int KEY_ESCAPE = KeyEvent.VK_ESCAPE;

    private boolean[] keys;
    private Game game;

    public KeyboardInput(Game game) {
        this.game = game;
        this.keys = new boolean[1024];
        for(int i = 0; i < 1024; i++) keys[i] = false;
    }

    public boolean isPressed(int i) {
        return this.keys[i];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(this.game.getWorld() != null) {
            if(this.game.getWorld().isShowTutorial()) {
                this.game.getWorld().setShowTutorial(false);
                return;
            }
        }
        this.keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }

    public void reset(int x) {
        this.keys[x] = false;
    }

}
