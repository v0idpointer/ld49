package me.v0idpointer.ld49.entity;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.input.KeyboardInput;
import me.v0idpointer.ld49.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {

    private boolean weaponMode = true;
    private int weaponTimer = 0;
    private int lastDir = 0;
    private float weaponCharge = 0.00f;

    private Texture[] staticSprites = {Texture.CHAR_MAIDEN_FRONT, Texture.CHAR_MAIDEN_BACK, Texture.CHAR_MAIDEN_RIGHT, Texture.CHAR_MAIDEN_LEFT};
    private Texture[] swordModels = {Texture.WEAPON_SWORDS_FRONT, Texture.WEAPON_SWORDS_BACK, Texture.WEAPON_SWORDS_RIGHT, Texture.WEAPON_SWORDS_LEFT};
    private Texture[] bowModels = {Texture.WEAPON_BOW_FRONT, Texture.WEAPON_BOW_BACK, Texture.WEAPON_BOW_RIGHT, Texture.WEAPON_BOW_LEFT};

    private Texture[] ANIMATION = { Texture.MAID_WALK_FORWARD, Texture.MAID_WALK_BACK, Texture.MAID_WALK_RIGHT, Texture.MAID_WALK_LEFT };

    private final int[][] arrowDir = { {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    private int weaponFireTimeout = 0;
    private boolean weaponPrimed = false;

    private Random random;

    private int animationTimer = 0;

    public Player(int x, int y, Game gameComponent) {
        super(0x01, x, y, "V0idPointer", gameComponent);
        this.random = new Random();
        this.setMaxHealth(180);
        this.setHealth(180);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        this.animationTimer++;
        if(this.animationTimer >= 64) this.animationTimer = 0;

        if(this.getX() + 32 > 128*64) this.setX(128*64 - 32);
        if(this.getY() + 64 > 128*64) this.setY(128*64 - 64);

        int dirX = 0, dirY = 0;
        if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_FORWARD)) dirY = -1;
        else if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_BACKWARD)) dirY = 1;
        else dirY = 0;

        if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_LEFT)) dirX = -1;
        else if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_RIGHT)) dirX = 1;
        else dirX = 0;

        if(dirX == 1) lastDir = 2;
        else if(dirX == -1) lastDir = 3;

        if(dirY == 1) lastDir = 0;
        else if(dirY == -1) lastDir = 1;

        int s = 4;

        int px = (this.getX() + 16) / 64;
        int py = (this.getY() + 32) / 64;
        try {
            if((this.getGameComponent().getWorld().getTileset().getRGB(px, py) & 0x00FFFFFF) == 0x0000FF) s = 1;
        }catch(Exception ex) { }

        this.setVelX((s * dirX));
        this.setVelY((s * dirY));

        if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_QUICK_SWITCH)) {
            if(this.weaponTimer == 0) {
                this.weaponMode = !this.weaponMode;
                this.weaponTimer = 128;
            }
        }

        if(this.weaponTimer > 0) {
            this.weaponTimer--;
            if(this.weaponTimer < 0) this.weaponTimer = 0;
        }

        if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_ATTACK) && this.weaponFireTimeout == 0) {
            this.weaponCharge += 0.005f;
            if(this.weaponCharge > 1.0f) this.weaponCharge = 1.0f;
            this.weaponPrimed = true;
        }
        else {
            if(this.weaponPrimed && this.weaponMode) {
                // fully charged attack
                if(this.weaponCharge > 0.90f) {
                    for(int i = 0; i < 8; i++) {
                        int[] a = this.arrowDir[i];
                        this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                        this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                        this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                    }
                }
                // medium charged
                else if(this.weaponCharge > 0.41f) {
                    int[] a = this.arrowDir[this.lastDir];
                    this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                    this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                    this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                    this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                    this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX() + random.nextInt(32), this.getY()+ random.nextInt(32), this.getGameComponent(), a[0], a[1], true));
                }
                // normal
                else {
                    int[] a = this.arrowDir[this.lastDir];
                    this.getGameComponent().getWorld().getEntities().add(new Arrow(this.getX(), this.getY(), this.getGameComponent(), a[0], a[1], true));
                }

                this.weaponCharge = 0;
                this.weaponFireTimeout = 4;
                this.weaponPrimed = false;
                Sound.fire.play();
            }
            if(this.weaponPrimed) {

                if(this.weaponCharge > 0.90f) {
                    for(int i = 0; i < 3; i++) {
                        this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX(), this.getY(), this.getGameComponent()));
                        this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX(), this.getY() - 64, this.getGameComponent()));
                        this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX() + 32, this.getY() - 32, this.getGameComponent()));
                        this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX() - 32, this.getY() - 32, this.getGameComponent()));
                    }
                }
                else{
                    if(this.lastDir == 0) this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX(), this.getY(), this.getGameComponent()));
                    else if(this.lastDir == 1) this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX(), this.getY() - 64, this.getGameComponent()));
                    else if(this.lastDir == 2) this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX() + 32, this.getY() - 32, this.getGameComponent()));
                    else if(this.lastDir == 3) this.getGameComponent().getWorld().getEntities().add(new SwordAttack(this.getX() - 32, this.getY() - 32, this.getGameComponent()));
                }

                this.weaponCharge = 0;
                this.weaponFireTimeout = 4;
                this.weaponPrimed = false;
            }
        }

        if(this.weaponFireTimeout != 0) {
            this.weaponFireTimeout--;
            if(this.weaponFireTimeout < 0) this.weaponFireTimeout = 0;
        }

        if(this.getGameComponent().getWorld().checkForCompletion()) {
            this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(10);
            this.getGameComponent().setGameState(GameState.MainMenu);
        }
        if(this.getHealth() > this.getMaxHealth()) this.setHealth(this.getMaxHealth());
        if(this.getHealth() <= 0) {
            this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(11);
            this.getGameComponent().setGameState(GameState.MainMenu);
        }

        if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_ESCAPE)) {
            this.getGameComponent().getDisplayComponent().getKeyboardInput().reset(KeyboardInput.KEY_ESCAPE);
            this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(2);
            this.getGameComponent().setGameState(GameState.MainMenu);
        }
    }

    @Override
    public void render(Graphics g) {
        if(this.getVelX() == 0 && this.getVelY() == 0) {
            g.drawImage(staticSprites[this.lastDir].getImage(), this.getX(), this.getY(), 32, 64, null);
            g.drawImage(( (!this.isWeaponMode()) ? swordModels : bowModels )[this.lastDir].getImage(), this.getX(), this.getY(), 32, 64, null);
        }
        else {
            int tx = 32 * ((this.animationTimer > 32) ? 1 : 0);
            Texture tt = null;
            int ii = 0;

            if(this.getVelX() < 0) ii = 3;
            if(this.getVelX() > 0) ii = 2;

            if(this.getVelY() < 0) ii = 1;
            if(this.getVelY() > 0) ii = 0;

            tt = ANIMATION[ii];
            g.drawImage(tt.getImage().getSubimage(0, tx, 16, 32), this.getX(), this.getY(), 32, 64, null);
            g.drawImage(( (!this.isWeaponMode()) ? swordModels : bowModels )[this.lastDir].getImage(), this.getX(), this.getY(), 32, 64, null);
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY() + 32, 32, 32);
    }

    public boolean isWeaponMode() {
        return weaponMode;
    }

    public int getWeaponTimer() {
        return this.weaponTimer;
    }

    public float getWeaponCharge() {
        return weaponCharge;
    }

}
