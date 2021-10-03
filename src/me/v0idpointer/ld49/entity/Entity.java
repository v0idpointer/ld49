package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;

import java.awt.*;

public abstract class Entity {

    private final int id;
    private int x, y;
    private String name;
    private final Game gameComponent;
    private int health = 100, maxHealth = 100;
    private int velX, velY;

    public Entity(int id, int x, int y, String name, Game gameComponent) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.gameComponent = gameComponent;
    }

    public abstract void update();
    public abstract void render(Graphics g);
    public abstract Rectangle getHitBox();

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGameComponent() {
        return gameComponent;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
}
