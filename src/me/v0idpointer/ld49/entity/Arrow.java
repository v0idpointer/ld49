package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.sfx.Sound;

import java.awt.*;

public class Arrow extends Entity {

    private int tx = 0, ty = 0;
    private int dirX, dirY;
    private boolean playerOwner;
    private int timer = 0;

    private final int damage = 12;

    public Arrow(int x, int y, Game gameComponent, int dirX, int dirY, boolean playerOwner) {
        super(0x03, x, y, "arrow", gameComponent);
        this.dirX = dirX;
        this.dirY = dirY;
        this.playerOwner = playerOwner;

        String q = "" + dirX + "," + dirY;
        switch(q) {

            default:
            case "-1,0":
                tx = 0;
                ty = 0;
                break;

            case "1,0":
                tx = 0;
                ty = 1;
                break;

            case "0,-1":
                tx = 0;
                ty = 2;
                break;

            case "0,1":
                tx = 0;
                ty = 3;
                break;

            case "-1,-1":
                tx = 1;
                ty = 0;
                break;

            case "1,-1":
                tx = 1;
                ty = 1;
                break;

            case "-1,1":
                tx = 1;
                ty = 2;
                break;

            case "1,1":
                tx = 1;
                ty = 3;
                break;
        }

    }

    @Override
    public void update() {
        this.setX(this.getX() + (7 * dirX));
        this.setY(this.getY() + (7 * dirY));

        this.timer ++;
        if(this.timer >= 640) {
            this.getGameComponent().getWorld().getEntities().remove(this);
        }

        if(this.getX() < 0 || this.getY() < 0)
            this.getGameComponent().getWorld().getEntities().remove(this);

        boolean fire = false;
        for(Entity entity : this.getGameComponent().getWorld().getEntities()) {
            if(entity instanceof Arrow || entity instanceof PropDynamic) continue;
            if(this.getHitBox().intersects(entity.getHitBox())) {

                if(this.playerOwner) {
                    if(!(entity instanceof Player)) {
                        entity.setHealth(entity.getHealth() - damage);
                        this.getGameComponent().getWorld().getEntities().remove(this);
                        fire = true;
                    }
                }
                else{
                    if(entity instanceof Player) {
                        entity.setHealth(entity.getHealth() - damage);
                        this.getGameComponent().getWorld().getEntities().remove(this);
                        fire = true;
                    }
                }

            }
            if(fire) Sound.hit.play();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Texture.ARROW_SPRITE_SHEET.getImage().getSubimage(tx * 16, ty * 16, 16, 16), this.getX(), this.getY(), 32, 32, null);
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), 32, 32);
    }

    public boolean isPlayerOwner() {
        return playerOwner;
    }
}
