package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.entity.Entity;
import me.v0idpointer.ld49.entity.Player;
import me.v0idpointer.ld49.entity.PropDynamic;
import me.v0idpointer.ld49.entity.PropStatic;
import me.v0idpointer.ld49.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityRender extends Impressive {

    public EntityRender(Game gameComponent) {
        super("entity_render", gameComponent);
    }

    @Override
    public void render() {
        BufferedImage image = new BufferedImage(DisplayComponent.WIDTH, DisplayComponent.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        int ox = 0, oy = 0;
        try {
            Player player = this.getGameComponent().getWorld().getPlayer();
            if(player != null) {
                ox = -player.getX() + 640/2;
                oy = -player.getY() + 480/2;
            }
        }catch(Exception ex) {}

        if(ox > 0) ox = 0;
        if(oy > 0) oy = 0;

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(ox, oy);

        if(this.getGameComponent().getGameState() == GameState.Game) {
            World world = this.getGameComponent().getWorld();
            for(Entity entity : world.getEntities()) {
                if(entity instanceof PropStatic || entity instanceof PropDynamic) continue;
                entity.render(g2d);
            }
        }

        g2d.translate(-ox, -oy);

        this.setNextBuffer(image);
        this.markBufferForSwap();
    }

    @Override
    public void update() {

    }
}
