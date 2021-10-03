package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;
import java.util.Random;

public class Slime extends Entity {

    private int animationTimer = 0;
    private int jumpTimer = 0;

    public Slime(int x, int y, Game gameComponent) {
        super(0xFF000001, x, y, "slime", gameComponent);
        this.setMaxHealth(22);
        this.setHealth(22);
    }

    @Override
    public void update() {
        this.animationTimer++;
        if(this.animationTimer >= 128) this.animationTimer = 0;

        if(this.getHealth() <= 0) {
            this.getGameComponent().getWorld().getEntities().remove(this);
        }

        if(this.jumpTimer == 0) {
            Random r = new Random();

            int dx = (r.nextInt(3) - 1);
            int dy = (r.nextInt(3) - 1);

            this.setX(this.getX() + (dx * 8));
            this.setY(this.getY() + (dy * 8));
            this.jumpTimer = r.nextInt(8) * 64;
        }

        this.jumpTimer--;
        if(this.jumpTimer < 0) this.jumpTimer = 0;

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Texture.SLIME_SPRITE_SHEET.getImage().getSubimage(0, ( (this.animationTimer > 64) ? 1 : 0) * 16, 16, 16), this.getX(), this.getY(), 32, 32, null);

        if(this.getHealth() != this.getMaxHealth()) {
            g.setColor(Color.gray);
            g.fillRect(this.getX(), this.getY() - 8, 32, 4);
            g.setColor(Color.green);
            g.fillRect(this.getX(), this.getY() - 8, (int)(32 * (this.getHealth() / ( this.getMaxHealth() * 1.0f ))), 4);
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), 32, 32);
    }
}
