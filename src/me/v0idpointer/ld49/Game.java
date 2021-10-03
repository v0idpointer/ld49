package me.v0idpointer.ld49;

import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.impressive.DisplayComponent;
import me.v0idpointer.ld49.impressive.Impressive;
import me.v0idpointer.ld49.sfx.Sound;
import me.v0idpointer.ld49.world.Tile;
import me.v0idpointer.ld49.world.World;

import java.util.Arrays;
import java.util.HashMap;

public class Game {

    public final int flags;
    public final HashMap<String, String> data;

    private final Window window;
    private final DisplayComponent displayComponent;

    private boolean isRunning = false;
    private Thread thread;
    private long secretCounter = 0;

    private GameState gameState = GameState.StartupScreen;
    private int scaryCounter = 0;

    private World world;
    private int stateCounter = 0;

    public Game(int flags, HashMap<String, String> data) {
        this.flags = flags;
        this.data = data;

        System.out.println("Force loading textures before Windows & DisplayComponent...");
        Texture.displayScale = 0;

        System.out.println("Force loading sounds before Windows & DisplayComponent...");
        Sound.defaultSound = null;

        this.window = new Window(fetchi("/w", 1280), fetchi("/h", 960), "Inferis Project", !this.fetchFlag("/nt"));
        this.displayComponent = new DisplayComponent(this, this.window);

        Texture.displayScale = this.displayComponent.getScale();
        System.out.println(Tile.GetRegisteredTiles() + " registered tiles.");

        World.CreateColorRef();

        System.out.println("Ready!");
        start();
    }

    public synchronized void start() {
        this.displayComponent.start();

        this.isRunning = true;
        this.thread = new Thread(this::run, "game");
        this.thread.start();
    }

    public synchronized void stop() {
        this.displayComponent.stop();

        this.isRunning = false;
        try {
            this.thread.join();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / 64.0;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while(this.isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                this.update();
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.displayComponent.updateFrameCounter();
            }
        }

        this.stop();
    }

    public void update() {
        long lastTime = System.currentTimeMillis();

        if(this.gameState == GameState.Game) {
            this.getDisplayComponent().getRenderThreads().get(0).pause();
            // this.getDisplayComponent().getRenderThreads().get(4).pause();
        }
        else {
            this.getDisplayComponent().getRenderThreads().get(0).resume();
            // this.getDisplayComponent().getRenderThreads().get(4).resume();
        }

        for(Impressive impressive : this.displayComponent.getRenderThreads())
            impressive.update();

        if(this.gameState == GameState.StartupScreen) {
            if(this.fetchFlag("/sv")) this.scaryCounter = Integer.MAX_VALUE - 1;
            this.scaryCounter++;
            if(this.scaryCounter >= 192) {
                this.scaryCounter = 0;
                this.setGameState(GameState.MainMenu);
            }
        }

        if(this.gameState == GameState.Game) {
            this.world.update();
        }

        this.stateCounter++;
        if(this.stateCounter >= 128) this.stateCounter = 128;

        this.secretCounter = (System.currentTimeMillis() - lastTime);
    }

    public String fetch(String address, String defaultValue) {
        return this.data.getOrDefault(address, defaultValue);
    }

    public int fetchi(String address, int defaultValue) {
        return Integer.parseInt( fetch(address, String.valueOf(defaultValue)) );
    }

    public float fetchf(String address, float defaultValue) {
        return Float.parseFloat( fetch(address, String.valueOf(defaultValue)) );
    }

    public boolean fetchFlag(String address) {
        int pos = Arrays.asList(Main.gameArguments).indexOf(address);

        if(pos < 0) return false;
        else return ( ( this.flags & (int)(Math.pow(2, pos)) ) != 0 );
    }

    public Window getWindow() {
        return this.window;
    }

    public DisplayComponent getDisplayComponent() {
        return this.displayComponent;
    }

    public long getSecretCounter() {
        return this.secretCounter;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        if(this.stateCounter >= 128) {
            GameState lastTime = this.gameState;
            this.gameState = gameState;
            System.out.println("State: " + lastTime + " -> " + this.gameState);
        }
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
