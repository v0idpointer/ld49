package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.input.KeyboardInput;
import me.v0idpointer.ld49.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class MemoryLeak extends Entity {

    private int animationTimer = 0, animationStage = 0;

    public MemoryLeak(int x, int y, Game gameComponent) {
        super(0x5500000C, x, y, "memory leak", gameComponent);
        Random random = new Random();
        this.setMaxHealth(40 + random.nextInt(70));
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public void update() {
        this.animationTimer++;
        if(this.animationTimer >= 8) {
            this.animationStage++;
            this.animationTimer = 0;
            if(this.animationStage >= 4) this.animationStage = 0;
        }

        for(Entity entity : this.getGameComponent().getWorld().getEntities()) {
            if(entity instanceof Player) {
                double d = Math.sqrt( (this.getX() - entity.getX())*(this.getX() - entity.getX()) + (this.getY() - entity.getY())*(this.getY() - entity.getY()) );
                if(d <= 128 && this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_USE)) {
                    if(this.getGameComponent().getWorld().getUtilityMemoryCrystal() >= 1) {
                        this.getGameComponent().getWorld().setUtilityMemoryCrystal(0);
                        this.getGameComponent().getWorld().setObjectiveStopMemoryLeak(1);
                        this.getGameComponent().getWorld().getEntities().remove(this);

                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), -1, -1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 0, -1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 1, -1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 1, 0, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 1, 1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), 0, 1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), -1, 1, 0xFfffffff));
                        this.getGameComponent().getWorld().getEntities().add(new Sparkle(this.getX() + 32, this.getY() + 32, this.getGameComponent(), -1, 0, 0xFfffffff));

                        Sound.death.play();
                    }
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Texture.ANOMALY_MEMORY_LEAK.getImage().getSubimage(0, animationStage * 16, 16, 16), this.getX(), this.getY(), 64, 64, null);
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), 32, 32);
    }
}
