package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.entity.Entity;
import me.v0idpointer.ld49.entity.MemoryLeak;
import me.v0idpointer.ld49.entity.VisualLeak;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.input.KeyboardInput;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class HudRender extends Impressive {

    private Random random;

    public HudRender(Game gameComponent) {
        super("hud_render", gameComponent);
        this.random = new Random();
    }

    @Override
    public void render() {
        BufferedImage image = new BufferedImage(DisplayComponent.WIDTH, DisplayComponent.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        if(this.getGameComponent().getGameState() == GameState.Game) {
            Graphics2D g2d = (Graphics2D)graphics;
            if(!this.getGameComponent().fetchFlag("/ni")) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                if(random.nextInt(2200) < 3) this.renderFuzz(g2d);
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.setColor(Color.gray);
            g2d.fillRect(8, 8, 64, 64);

            g2d.fillRect(80, 8, 128, 16);
            if(this.getGameComponent().getWorld().getPlayer() != null)
                if(this.getGameComponent().getWorld().getPlayer().getWeaponCharge() > 0.20f)
                    g2d.fillRect(256 - 32, 8, 128, 16);

            if(this.getGameComponent().getWorld() != null) {
                g2d.fillRect(80, 32, 32, 32);
                if(this.getGameComponent().getWorld().getUtilityMemoryCrystal() >= 1)
                    g2d.drawImage(Texture.HEAL_CRYSTAL.getImage(), 80, 32, 32, 32, null);
                g2d.fillRect(120, 32, 32, 32);
                if(this.getGameComponent().getWorld().getUtilityVisualCrystal() >= 1)
                    g2d.drawImage(Texture.VISUAL_CRYSTAL.getImage(), 120, 32, 32, 32, null);
                g2d.fillRect(160, 32, 32, 32);
                if(this.getGameComponent().getWorld().getUtilityVisualCrystal() >= 2)
                    g2d.drawImage(Texture.VISUAL_CRYSTAL.getImage(), 160, 32, 32, 32, null);
            }

            g2d.fillRect(488, 8, 136, 128);

            if(this.getGameComponent().getWorld().getPlayer() != null) {

                int cooldown = this.getGameComponent().getWorld().getPlayer().getWeaponTimer();
                g2d.setColor(Color.darkGray);
                g2d.fillRect(8, 72 + (int)( 64 * (cooldown / -128.0f) ), 64, 64 - (int)( 64 * ( (128 - cooldown) / 128.0f) ));

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                g2d.drawImage(( (this.getGameComponent().getWorld().getPlayer().isWeaponMode()) ? Texture.UI_BOW_N_ARROW : Texture.UI_DUAL_BLADES ).getImage(), 16, 16, 48, 48, null);

                int h = this.getGameComponent().getWorld().getPlayer().getHealth();
                int m = this.getGameComponent().getWorld().getPlayer().getMaxHealth();

                g2d.setColor(Color.green);
                if(h < m/4) g2d.setColor(Color.red);
                g2d.fillRect(84, 12, (int)( 120 * ( h / (m * 1.0f) ) ), 8);

                g2d.setColor(Color.white);
                g2d.drawString("Q", 64, 72);
                g2d.drawString(h + "/" + m, 96, 20);

                g2d.drawString("Objectives:", 500, 24);
                g2d.drawString("Destroy Anomalies " + ( this.getGameComponent().getWorld().getObjectiveDestroyAnomalies() ) + "/8", 492, 48);
                g2d.drawString("Eliminate Nullmen " + + ( this.getGameComponent().getWorld().getObjectiveDestroyNullMen() ) + "/4", 492, 64);
                g2d.drawString("Destroy Vis-leaks " + ( this.getGameComponent().getWorld().getObjectiveStopVisualLeak() ) + "/2", 492, 80);
                g2d.drawString("Destroy Mem-leaks " + ( this.getGameComponent().getWorld().getObjectiveStopMemoryLeak() ) + "/1", 492, 96);
                g2d.drawString("Press and hold 'M'", 492, 112);
                g2d.drawString("for more information.", 492, 128);

                if(this.getGameComponent().getWorld().getPlayer().getWeaponCharge() > 0.20f) {
                    g2d.setColor(Color.darkGray);
                    if(this.getGameComponent().getWorld().getPlayer().getWeaponCharge() >= 0.5f) g2d.setColor(Color.green);
                    if(this.getGameComponent().getWorld().getPlayer().getWeaponCharge() >= 1.00f) g2d.setColor(Color.red);
                    g2d.fillRect(228, 12, (int)(120 * this.getGameComponent().getWorld().getPlayer().getWeaponCharge() ), 8);
                }

                for(Entity entity : this.getGameComponent().getWorld().getEntities()) {
                    if(entity instanceof MemoryLeak || entity instanceof VisualLeak) {
                        int x1, x2, y1, y2;
                        x1 = entity.getX();
                        x2 = this.getGameComponent().getWorld().getPlayer().getX();
                        y1 = entity.getY();
                        y2 = this.getGameComponent().getWorld().getPlayer().getY();
                        double d = Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
                        if(d < 128) {
                            g2d.setColor(Color.white);
                            g2d.drawString("Press F to use a crystal...", 80, 80);
                        }
                    }
                }

            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            if(this.getGameComponent().getWorld().getPlayer() != null) {
                if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_OPEN_MAP)) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                    g2d.setColor(Color.darkGray);
                    g2d.fillRect(0, 0, 640, 480);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                    g2d.setColor(Color.white);
                    g2d.drawString("Search for objectives in highlighted areas...", 320-124, 96);
                    g2d.drawImage(Texture.UX_MAP.getImage(), 320-128, 240-128, 256, 256, null);

                    int px = this.getGameComponent().getWorld().getPlayer().getX();
                    int py = this.getGameComponent().getWorld().getPlayer().getY();

                    px = (int)( (px / 8192.0f) * 256 );
                    py = (int)( (py / 8192.0f) * 256 );

                    BufferedImage playerOverlay = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
                    Graphics gfx = playerOverlay.createGraphics();

                    gfx.setColor(Color.magenta);
                    gfx.fillRect(63, 64, 4, 4);

                    gfx.setColor(Color.yellow);
                    gfx.fillRect(202, 38, 4, 4);

                    gfx.setColor(Color.cyan);
                    gfx.fillRect(220, 200, 4, 4);

                    gfx.setColor(Color.green);
                    gfx.fillRect(px - 2, py - 2, 4, 4);

                    g2d.drawImage(playerOverlay, 320-128, 240-128, 256, 256, null);

                    g2d.drawString("You", 150, 128+64);
                    g2d.drawString("City of Eternal Woods", 54, 128+64+16);
                    g2d.drawString("Lonely Tree", 107, 128+64+32);
                    g2d.drawString(" Quarantine", 107, 128+64+32+16);

                    g2d.setColor(Color.green);
                    g2d.fillRect(150 + 32, 128+64-6, 4, 4);

                    g2d.setColor(Color.magenta);
                    g2d.fillRect(150 + 32, 128+64+16 -6, 4, 4);

                    g2d.setColor(Color.yellow);
                    g2d.fillRect(150 + 32, 128+64+32 -6, 4, 4);

                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(150 + 32, 128+64+32+16 -6, 4, 4);

                }

               if(this.getGameComponent().getWorld().isShowTutorial()) {
                   g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                   g2d.setColor(Color.darkGray);
                   g2d.fillRect(0, 0, 640, 480);
                   g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                   g2d.setColor(Color.white);
                   g2d.drawString("Hello TheHappyHamburger70#@~*&847%6e5$", 320-156, 80);
                   g2d.drawString("Welcome to Inferis Project! Let's learn some basics...", 320-156, 96);

                   g2d.drawString("Use W,A,S,D to move around.", 320-156, 128);
                   g2d.drawString("Use SPACE to attack. You can use Q to switch", 320-156, 128+16);
                   g2d.drawString("between ranged and melee attacks.", 320-156, 128+32);

                   g2d.drawString("Press and hold M to open the map. Use the map to", 320-156, 128+64);
                   g2d.drawString("search for objectives. Attack opponents or use", 320-156, 128+64+16);
                   g2d.drawString("crystals to destroy Visual or Memory leaks. Press", 320-156, 128+64+32);
                   g2d.drawString("F when near a Leak to fix it.", 320-156, 128+64+32+16);

                   g2d.drawString("The HUD will display your health and weapon", 320-156, 256 + 16);
                   g2d.drawString("charge progress. You can hold down SPACE to", 320-156, 256 + 32);
                   g2d.drawString("charge your weapons, in order to perform powerful", 320-156, 256 + 48);
                   g2d.drawString("attacks.", 320-156, 256 + 64);

                   g2d.drawString("Press any key to continue...", 320-64-16-8, 256+128);

                   g2d.drawImage(Texture.SETUP01.getImage(), 16, 96, 128, 256, null);
               }

            }

        }

        this.setNextBuffer(image);
        this.markBufferForSwap();
    }

    @Override
    public void update() {

    }

    public void renderFuzz(Graphics graphics) {
        BufferedImage frame = new BufferedImage(160, 120, BufferedImage.TYPE_INT_ARGB);
        for(int yy = 0; yy < 120; yy++) {
            for(int xx = 0; xx < 160; xx++) {
                if(random.nextBoolean()) frame.setRGB(xx, yy, 0xFF808080);
            }
        }
        graphics.drawImage(frame, 0, 0, 640, 480, null);
    }

}
