package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;

public class Sparkle extends Entity {

    private int dirX, dirY;
    private int timer = 0;

    private final int color ;

    public Sparkle(int x, int y, Game gameComponent, int dirX, int dirY, int color) {
        super(0x03, x, y, "sparkle", gameComponent);
        this.dirX = dirX;
        this.dirY = dirY;
        this.color = color;
    }

    @Override
    public void update() {
        this.setX(this.getX() + (7 * dirX));
        this.setY(this.getY() + (7 * dirY));

        this.timer ++;
        if(this.timer >= 640) {
            this.getGameComponent().getWorld().getEntities().remove(this);
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(this.color));
        g.fillRect(this.getX() - 2, this.getY() - 2, 4, 4);
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(0, 0, 0, 0);
    }

}
