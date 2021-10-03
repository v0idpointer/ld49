package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.sfx.Sound;

import java.awt.*;

public class SwordAttack extends Entity {

    public SwordAttack(int x, int y, Game gameComponent) {
        super(0xff, x, y, null, gameComponent);

        boolean fire = false;
        for(Entity entity : this.getGameComponent().getWorld().getEntities()) {
            if(entity != this && !(entity instanceof Player)) {
                if(entity.getHitBox().intersects(this.getHitBox()))
                {
                    entity.setHealth(entity.getHealth() - 58);
                    fire = true;
                }
            }
        }

        if(fire) Sound.hit.play();
    }

    @Override
    public void update() {
        this.getGameComponent().getWorld().getEntities().remove(this);
    }

    @Override
    public void render(Graphics g) {
        // g.setColor(Color.red);
        // g.fillRect(this.getX(), this.getY() + 48, 32, 48);
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY() + 48, 32, 32);
    }
}
