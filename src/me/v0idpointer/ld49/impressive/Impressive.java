package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;

import java.awt.image.BufferedImage;

public abstract class Impressive {

    private final String name;
    private long timer = 0;

    private boolean isRunning = false, isPaused = false;
    private Thread thread;

    private BufferedImage buffer = null;
    private BufferedImage nextBuffer = null;
    private boolean isBufferReady = false;

    private final Game gameComponent;

    public Impressive(String name, Game gameComponent) {
        this.name = name;
        this.gameComponent = gameComponent;
    }

    public void run() {
        while(this.isRunning) {
            if(!this.isPaused) {
                long lastTime = System.currentTimeMillis();
                this.render();
                this.timer = ( System.currentTimeMillis() - lastTime );
            }
        }
    }

    public abstract void render();
    public abstract void update();

    public synchronized void start() {
        this.isRunning = true;
        this.thread = new Thread(this::run, this.name);
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

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    public long getTimer() {
        return this.timer;
    }

    public void resetTimer() {
        this.timer = 0;
    }

    public BufferedImage getBuffer() {
        return this.buffer;
    }

    public void swap() {
        if(this.isBufferReady) {
            this.buffer = this.nextBuffer;
            this.isBufferReady = false;
        }
    }

    public void markBufferForSwap() {
        this.isBufferReady = true;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return ( (this.isPaused) ? "sleeping" : "working" );
    }

    public void setNextBuffer(BufferedImage image) {
        this.nextBuffer = image;
    }

    public Game getGameComponent() {
        return this.gameComponent;
    }

}
