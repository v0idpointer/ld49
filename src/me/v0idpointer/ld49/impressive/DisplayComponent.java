package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.Main;
import me.v0idpointer.ld49.Window;
import me.v0idpointer.ld49.input.KeyboardInput;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class DisplayComponent extends Canvas {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Game game;
    private Window window;

    private Thread thread;
    private boolean isRunning = false;

    private LinkedList<Impressive> renderThreads;

    private int secretCounter = 0;
    private int frameCounter = 0;

    private float scale;

    private int controlMainMenuScreen = 0;

    private KeyboardInput keyboardInput;

    public DisplayComponent(Game game, Window window) {
        this.game = game;
        this.window = window;

        this.keyboardInput = new KeyboardInput(this.game);
        this.addKeyListener(keyboardInput);

        this.scale = this.game.fetchf("/s", 0);
        if(this.scale == 0) this.createScale(this.window.getFrame().getWidth());

        this.renderThreads = new LinkedList<>();
        attachThread(new BackgroundRender(this.game));

        attachThread(new WorldRender(this.game));
        attachThread(new EntityRender(this.game));
        attachThread(new HudRender(this.game));

        InterfaceRender ir = new InterfaceRender(this.game);
        this.addMouseListener(ir);
        this.addKeyListener(ir);
        attachThread(ir);

        this.window.getFrame().add(this);
        this.window.getFrame().setVisible(true);
    }

    public synchronized void start() {
        this.isRunning = true;
        this.thread = new Thread(this::run, "outputMerger");
        this.thread.start();
    }

    public synchronized void stop() {
        this.isRunning = false;

        try {
            this.thread.join();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        while(this.isRunning) render();
    }

    public void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, this.window.getFrame().getWidth(), this.window.getFrame().getHeight());

        for(Impressive impressive : this.renderThreads) {
            if(impressive instanceof InterfaceRender && this.game.getGameState() == GameState.Game) continue;
            impressive.swap();

            BufferedImage frame = impressive.getBuffer();
            graphics.drawImage(frame, 0, 0, (int)(WIDTH * this.scale), (int)(HEIGHT * this.scale), null);
        }

        if(this.game.fetchFlag("/v")) {
            graphics.setColor(Color.white);

            graphics.drawString(Main.PROJECT, 32, 32);
            graphics.drawString("v. " + Main.VERSION, 32, 48);

            graphics.drawString("Impressive:", 32, 64);
            graphics.drawString("Output Merger: " + frameCounter + "fps", 48, 64+16);

            int yo = 0;
            for(Impressive impressive : this.renderThreads) {
                graphics.drawString(impressive.getName() + ": " + impressive.getStatus() + "; " + impressive.getTimer() + "ms", 64, 96+yo);
                yo += 16;
            }

            graphics.drawString("@ 64 ticks; " + this.game.getSecretCounter() + "ms per tick", 32, 112+yo);
        }

        graphics.dispose();
        bs.show();

        secretCounter++;
    }

    public void attachThread(Impressive impressive) {
        this.renderThreads.add(impressive);
        impressive.start();
    }

    public void killThread(Impressive impressive) {
        impressive.pause();
        impressive.stop();
        this.renderThreads.remove(impressive);
    }

    public LinkedList<Impressive> getRenderThreads() {
        return this.renderThreads;
    }

    public void updateFrameCounter() {
        this.frameCounter = this.secretCounter;
        this.secretCounter = 0;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void createScale(int width) {
        this.scale = ( width / 640.0f );
    }

    public int getControlMainMenuScreen() {
        return controlMainMenuScreen;
    }

    public void setControlMainMenuScreen(int controlMainMenuScreen) {
        this.controlMainMenuScreen = controlMainMenuScreen;
    }

    public KeyboardInput getKeyboardInput() {
        return keyboardInput;
    }
}
