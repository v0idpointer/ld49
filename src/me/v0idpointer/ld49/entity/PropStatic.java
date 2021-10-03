package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;

public class PropStatic extends Entity {

    private Texture texture;
    private int dx, dy;

    public PropStatic(int x, int y, Game gameComponent, Texture texture, int dx, int dy) {
        super(0x0A, x, y, "prop_static", gameComponent);
        this.texture = texture;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void update() {
        for(Entity entity : this.getGameComponent().getWorld().getEntities()) {
            if(this.getHitBox().intersects(entity.getHitBox())) {

                int xz = entity.getVelX();
                int yz = entity.getVelY();

                entity.setX(entity.getX() + (xz * -2));
                entity.setY(entity.getY() + (yz * -2));

                entity.setVelX(0);
                entity.setVelY(0);
            }
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), dx, dx);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture.getImage(), this.getX(), this.getY(), dx, dy, null);
    }

}
