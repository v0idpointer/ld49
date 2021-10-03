package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;
import java.util.Random;

public class Nullman extends Entity {

    private int jumpTimer = 0;
    private boolean hasAi;
    private int attackTimer = 0;

    public Nullman(int x, int y, Game gameComponent, boolean hasAi) {
        super(0xFF00000F, x, y, "null man", gameComponent);
        this.hasAi = hasAi;
        Random random = new Random();
        this.setMaxHealth(20 + random.nextInt(10));
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public void update() {
        if(this.getHealth() <= 0) {
            this.getGameComponent().getWorld().getEntities().remove(this);
            this.getGameComponent().getWorld().setObjectiveDestroyNullMen(this.getGameComponent().getWorld().getObjectiveDestroyNullMen() + 1);
            this.getGameComponent().getWorld().getPlayer().setHealth( this.getGameComponent().getWorld().getPlayer().getHealth() + 15 );
        }

        if(this.hasAi) {
            if(this.getGameComponent().getWorld().getPlayer() != null) {
                double d = Math.sqrt( (this.getX() - this.getGameComponent().getWorld().getPlayer().getX())*(this.getX() - this.getGameComponent().getWorld().getPlayer().getX()) + (this.getY() - this.getGameComponent().getWorld().getPlayer().getY())*(this.getY() - this.getGameComponent().getWorld().getPlayer().getY()) );
                if(d < 5*64) {
                    if(this.jumpTimer == 0) {

                        this.setX(this.getGameComponent().getWorld().getPlayer().getX());
                        this.setY(this.getGameComponent().getWorld().getPlayer().getY());

                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), -1, -1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 0, -1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 1, -1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 1, 0, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 1, 1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 0, 1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), -1, 1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), -1, 0, 0xFfffffff));

                        this.jumpTimer = 64*3;

                        this.getGameComponent().getWorld().getPlayer().setHealth( this.getGameComponent().getWorld().getPlayer().getHealth() - 35 );

                    }
                }
            }
        }

        if(this.attackTimer > 0) {
            this.attackTimer--;
            if(this.attackTimer < 0) this.attackTimer = 0;
        }

        this.jumpTimer--;
        if(this.jumpTimer < 0) this.jumpTimer = 0;

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Texture.ANOMALY_NULL_MAN.getImage(), this.getX(), this.getY(), 32, 64, null);

        if(this.getHealth() != this.getMaxHealth()) {
            g.setColor(Color.gray);
            g.fillRect(this.getX(), this.getY() - 8, 32, 4);
            g.setColor(Color.green);
            g.fillRect(this.getX(), this.getY() - 8, (int)(32 * (this.getHealth() / ( this.getMaxHealth() * 1.0f ))), 4);
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), 32, 64);
    }
}
