package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;

public class PropDynamic extends Entity {

    private Texture texture;
    private int dx, dy;
    boolean isTransparent = false;

    public PropDynamic(int x, int y, Game gameComponent, Texture texture, int dx, int dy) {
        super(0x0A, x, y, "prop_static", gameComponent);
        this.texture = texture;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void update() {
        this.isTransparent = false;
        for(Entity entity : this.getGameComponent().getWorld().getEntities()) {
            if(entity instanceof Player && entity.getHitBox().intersects(getHitBox())) {
                this.isTransparent = true;
            }
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), dx, dy);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ( (this.isTransparent) ? 0.20f : 1.0f ) ));
        g2d.drawImage(texture.getImage(), this.getX(), this.getY(), dx, dy, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f ));
    }

}
