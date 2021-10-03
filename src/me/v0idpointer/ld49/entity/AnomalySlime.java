package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;
import java.util.Random;

public class AnomalySlime extends Entity {

    private int animationTimer = 0, animationStage = 0;
    private int jumpTimer = 0;
    private boolean hasAi;
    private int attackTimer = 0;

    public AnomalySlime(int x, int y, Game gameComponent, boolean hasAi) {
        super(0xFF00000A, x, y, "slime", gameComponent);
        this.hasAi = hasAi;
        Random random = new Random();
        this.setMaxHealth(40 + random.nextInt(70));
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public void update() {
        this.animationTimer++;
        if(this.animationTimer >= 128) {
            this.animationStage++;
            this.animationTimer = 0;
            if(this.animationStage >= 4) this.animationStage = 0;
        }

        if(this.getHealth() <= 0) {
            this.getGameComponent().getWorld().getEntities().remove(this);
            this.getGameComponent().getWorld().setObjectiveDestroyAnomalies(this.getGameComponent().getWorld().getObjectiveDestroyAnomalies() + 1);
        }

        if(this.hasAi) {
            if(this.getGameComponent().getWorld().getPlayer() != null) {
                double d = Math.sqrt( (this.getX() - this.getGameComponent().getWorld().getPlayer().getX())*(this.getX() - this.getGameComponent().getWorld().getPlayer().getX()) + (this.getY() - this.getGameComponent().getWorld().getPlayer().getY())*(this.getY() - this.getGameComponent().getWorld().getPlayer().getY()) );
                if(d < 5*64) {
                    if(this.jumpTimer == 0) {
                        Random r = new Random();

                        int dx = this.getGameComponent().getWorld().getPlayer().getX() - this.getX();
                        int dy = this.getGameComponent().getWorld().getPlayer().getY() - this.getY();

                        int dirX = 0, dirY = 0;

                        if (dx < 0) dirX = -1;
                        if (dx > 0) dirX = +1;
                        if (dy < 0) dirY = -1;
                        if (dy > 0) dirY = +1;

                        this.setX(this.getX() + (dirX * 12));
                        this.setY(this.getY() + (dirY * 12));
                        this.jumpTimer = 64;
                    }
                }
            }
        }

        if(this.getGameComponent().getWorld().getPlayer() != null) {
            if(this.getHitBox().intersects(this.getGameComponent().getWorld().getPlayer().getHitBox())) {
                if(this.attackTimer == 0) {
                    this.getGameComponent().getWorld().getPlayer().setHealth(this.getGameComponent().getWorld().getPlayer().getHealth() - 22);
                    this.attackTimer = 80;
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
        g.drawImage(Texture.ANOMALY_SLIME_SPRITE_SHEET.getImage().getSubimage(0, animationStage * 16, 16, 16), this.getX(), this.getY(), 32, 32, null);

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
