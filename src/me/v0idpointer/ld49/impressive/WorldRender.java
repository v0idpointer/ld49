package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.entity.*;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.world.Tile;
import me.v0idpointer.ld49.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class WorldRender extends Impressive {

    private Texture[][] patch = { {Texture.GRASS_UP_SIDE, Texture.NULL_GRASS_TOP}, {Texture.GRASS_BOTTOM_SIDE, Texture.NULL_GRASS_BOTTOM},
            {Texture.GRASS_RIGHT_SIDE, Texture.NULL_GRASS_RIGHT}, {Texture.GRASS_LEFT_SIDE, Texture.NULL_GRASS_LEFT} };

    public WorldRender(Game gameComponent) {
        super("world", gameComponent);
    }

    @Override
    public void render() {
        BufferedImage image = new BufferedImage(DisplayComponent.WIDTH, DisplayComponent.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        if(this.getGameComponent().getGameState() == GameState.Game) {

            Player player = this.getGameComponent().getWorld().getPlayer();
            int ox = 0, oy = 0;
            if(player != null) {
                ox = -player.getX() + 640/2;
                oy = -player.getY() + 480/2;
            }

            if(ox > 0) ox = 0;
            if(oy > 0) oy = 0;

            Graphics2D g2d = (Graphics2D)graphics;
            g2d.translate(ox, oy);

            int px, py;
            px = (this.getGameComponent().getWorld().getPlayer().getX() / 64);
            py = (this.getGameComponent().getWorld().getPlayer().getY() / 64);

            World world = this.getGameComponent().getWorld();
            for(int xx = 0; xx < world.getTileset().getWidth(); xx++) {
                for(int yy = 0; yy < world.getTileset().getHeight(); yy++) {
                    double d = Math.sqrt( (xx - px)*(xx - px) + (yy - py)*(yy - py) );
                    if(d > 12d) continue;

                    int rgb = world.getTileset().getRGB(xx, yy);
                    rgb = (rgb & 0x00FFFFFF);

                    if(rgb == 0xFF00FF) {
                        rgb = 0x00FF00;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFF00FF00);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new PropDynamic(xx * 64 - 28, yy * 64 - 128-64, this.getGameComponent(), Texture.PROP_PURPLE_TREE, 128, 256));
                    }

                    if(rgb == 0x555555) {
                        rgb = 0x808080;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFF808080);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new PropStatic(xx * 64, yy * 64, this.getGameComponent(), Texture.PROP_WALL, 64, 64));
                    }

                    if(rgb == 0x666666) {
                        rgb = 0x808080;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFF808080);
                        /*if(world.isInit())*/  this.getGameComponent().getWorld().getEntities().add(new PropStatic(xx * 64, yy * 64, this.getGameComponent(), Texture.PROP_WALL_VERTICAL, 64, 64));
                    }

                    if(rgb == 0x777777) {
                        rgb = 0x808080;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFF808080);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new PropStatic(xx * 64, yy * 64, this.getGameComponent(), Texture.PROP_WALL_CONNECTION, 64, 64));
                    }

                    if(rgb == 0xFFFF00) {
                        rgb = 0x00FF00;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFF00FF00);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new MemoryLeak(xx * 64, yy * 64, this.getGameComponent()));
                    }

                    if(rgb == 0xFFFFAA) {
                        rgb = 0xFF0000;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFFFF0000);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new VisualLeak(xx * 64, yy * 64, this.getGameComponent()));
                    }

                    if(rgb == 0x404080) {
                        rgb = 0x404040;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0x404040);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new AnomalySlime(xx * 64, yy * 64, this.getGameComponent(), false));
                    }

                    if(rgb == 0x4040CC) {
                        rgb = 0x404040;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0x404040);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new AnomalySlime(xx * 64, yy * 64, this.getGameComponent(), true));
                    }

                    if(rgb == 0x4040FF) {
                        rgb = 0x00FF00;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0x00FF00);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new AnomalySlime(xx * 64, yy * 64, this.getGameComponent(), true));
                    }

                    if(rgb == 0xB200FF) {
                        rgb = 0xFF0000;
                        this.getGameComponent().getWorld().getTileset().setRGB(xx, yy, 0xFF0000);
                        /*if(world.isInit())*/ this.getGameComponent().getWorld().getEntities().add(new Nullman(xx * 64, yy * 64, this.getGameComponent(), true));
                    }

                    if(rgb == 0)  continue;
                    if(rgb == 0xFFFFFF) System.out.println("VoidPointer says: \"White pixel at " + xx + ", " + yy + " -> " + (xx * 64) + ", " + (yy * 64));

                    int id = World.COLOR_REF[rgb];
                    if(id == 0) continue;

                    Texture texture = Tile.tiles[id].getTexture();
                    if(shouldVisualGlitch()) texture = Texture.DEFAULT;
                    graphics.drawImage(texture.getImage(), xx * 64, yy * 64, 64, 64, null);

                    // absolute aids
                    if(id != 1 && id != 6) {

                        int tn = 0, ts = 0, te = 0, tw = 0;

                        boolean n = false;
                        try {
                            if((world.getTileset().getRGB(xx, yy - 1) & 0x00FFFFFF) == 0x00FF00) {
                                n = true;
                                tn = 0;
                            }
                            if((world.getTileset().getRGB(xx, yy - 1) & 0x00FFFFFF) == 0xFF0000) {
                                n = true;
                                tn = 1;
                            }
                        }catch(Exception ex) { }

                        boolean s = false;
                        try {
                            if((world.getTileset().getRGB(xx, yy + 1) & 0x00FFFFFF) == 0x00FF00) {
                                s = true;
                                ts = 0;
                            }
                            if((world.getTileset().getRGB(xx, yy + 1) & 0x00FFFFFF) == 0xFF0000) {
                                s = true;
                                ts = 1;
                            }
                        }catch(Exception ex) { }

                        boolean e = false;
                        try {
                            if((world.getTileset().getRGB(xx + 1, yy) & 0x00FFFFFF) == 0x00FF00) {
                                e = true;
                                te = 0;
                            }
                            if((world.getTileset().getRGB(xx + 1, yy) & 0x00FFFFFF) == 0xFF0000) {
                                e = true;
                                te = 1;
                            }
                        }catch(Exception ex) { }

                        boolean w = false;
                        try {
                            if((world.getTileset().getRGB(xx - 1, yy) & 0x00FFFFFF) == 0x00FF00) {
                                w = true;
                                tw = 0;
                            }
                            if((world.getTileset().getRGB(xx - 1, yy) & 0x00FFFFFF) == 0xFF0000) {
                                w = true;
                                tw = 1;
                            }
                        }catch(Exception ex) { }

                        if(n) graphics.drawImage(patch[0][tn].getImage(), xx * 64, yy * 64, 64, 64, null);
                        if(s) graphics.drawImage(patch[1][ts].getImage(), xx * 64, yy * 64, 64, 64, null);
                        if(e) graphics.drawImage(patch[2][te].getImage(), xx * 64, yy * 64, 64, 64, null);
                        if(w) graphics.drawImage(patch[3][tw].getImage(), xx * 64, yy * 64, 64, 64, null);
                    }

                }
            }

            for(Entity entity : world.getEntities()) {
                if(entity instanceof PropStatic || entity instanceof PropDynamic)
                    entity.render(graphics);
            }

            g2d.translate(-ox, -oy);

            // if(world.isInit()) world.setInit(false);
        }

        this.setNextBuffer(image);
        this.markBufferForSwap();
    }

    @Override
    public void update() { }

    private boolean shouldVisualGlitch() {
        if(this.getGameComponent().fetchFlag("/ni")) return false;
        else {
            Random random = new Random();
            if(random.nextInt(25000) < 3) return true;
            else return false;
        }
    }

}
