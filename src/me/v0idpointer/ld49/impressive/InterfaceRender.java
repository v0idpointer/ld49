package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.Main;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.input.KeyboardInput;
import me.v0idpointer.ld49.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class InterfaceRender extends Impressive implements MouseListener, KeyListener {

    private Font titleFont, mainFont, smallFont, console;

    private float focusAlpha = 0, focusDir = 1.0f;
    private int cursorTimer = 0;
    private boolean cursorBlink = false;
    private String username = "";
    private boolean gender = true;
    private int errorTimer = -1;
    private final String[] femaleSkins = {"Default", "Mage", "Maid", "Summer Attire", "Scarlet Dream"};
    private final String[] maleSkins = {"Default", "Knight", "Illuminated Assassin", "Nobleman's Attire", "Butler's Attire"};
    private int selectedSkin = 0;
    private int scene = 0;
    private int sceneTimer = 0;
    private int victoryTimer = 0;
    private int helpPage = 0;

    public InterfaceRender(Game gameComponent) {
        super("ui_render", gameComponent);

        this.titleFont = new Font("Impact", 0, 55);
        this.mainFont = new Font("Impact", 0, 24);
        this.smallFont = new Font("Impact", 0, 16);
        this.console = new Font("Consolas", 0, 18);
    }

    @Override
    public void render() {
        BufferedImage image = new BufferedImage(DisplayComponent.WIDTH, DisplayComponent.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        if(this.getGameComponent().getGameState() == GameState.MainMenu) {

            switch(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen()) {

                case 0:
                    renderMainMenu(graphics);
                    break;

                case 1:
                    renderCharacterCreation(graphics);
                    break;

                case 2:
                    renderPause(graphics);
                    break;

                case 5:
                    renderCutscene(graphics);
                    break;

                case 10:
                    renderVictory(graphics);
                    break;

                case 11:
                    renderDefeat(graphics);
                    break;

                case 15:
                    renderVoidptr(graphics);
                    break;

                case 20:
                    renderAbout(graphics);

            }

        }

        this.setNextBuffer(image);
        this.markBufferForSwap();
    }

    @Override
    public void update() {
        if(this.getGameComponent().getGameState() == GameState.MainMenu) {

            if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 10 ||
            this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 11) {
                this.victoryTimer++;
                if(this.victoryTimer >= 64*6) {
                    this.victoryTimer = 0;
                    this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(15);
                }
            }

            if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() >= 2) {

                if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 5) {
                    this.sceneTimer++;
                    if(this.sceneTimer >= 64 * (( this.scene == 2 ) ? 18 : 12)) {
                        if(this.scene+1 >= 4) {
                            this.scene = 0;
                            this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(1);
                        }
                        this.scene++;
                        this.sceneTimer = 0;
                    }
                    if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_ATTACK)) {
                        this.getGameComponent().getDisplayComponent().getKeyboardInput().reset(KeyboardInput.KEY_ATTACK);
                        if(this.scene + 1 >= 4) {
                            this.scene = 0;
                            this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(1);
                        }
                        this.scene++;
                    }
                    return;
                }

                if(this.getGameComponent().getDisplayComponent().getKeyboardInput().isPressed(KeyboardInput.KEY_ESCAPE)) {
                    this.getGameComponent().getDisplayComponent().getKeyboardInput().reset(KeyboardInput.KEY_ESCAPE);
                    this.getGameComponent().setGameState(GameState.Game);
                }

            }else{
                this.cursorTimer++;
                if(this.cursorTimer > 24) {
                    this.cursorTimer = 0;
                    this.cursorBlink = !this.cursorBlink;
                }

                if(this.errorTimer >= 0) {
                    this.errorTimer ++;
                    if(this.errorTimer >= 320) {
                        this.getGameComponent().getWorld().setShowTutorial(true);
                        this.getGameComponent().setGameState(GameState.Game);
                        this.errorTimer = -1;
                    }
                }
            }
        }
    }

    private void renderMainMenu(Graphics graphics) {
        graphics.setFont(this.titleFont);

        graphics.drawImage(Texture.MAIN_MENU_BANNER.getImage(), 0, 0, 640 ,480, null);

        graphics.setColor(Color.darkGray);
        graphics.drawString("Inferis Project", 72, 72);
        graphics.setColor(Color.white);
        graphics.drawString("Inferis Project", 64, 64);

        graphics.setFont(this.mainFont);
        graphics.drawString("Play", 64, 128+64);
        // graphics.drawString("Options", 64, 128+64+32);
        graphics.drawString("Help", 64, 256);
        graphics.drawString("Exit", 64, 256+32);

        graphics.setFont(this.smallFont);
        graphics.drawString(Main.VERSION, 32, 420);

    }

    private void renderFocusNagger(Graphics graphics) {
        if(this.getGameComponent().fetchFlag("/nf")) return;

        Graphics2D g2d = (Graphics2D)graphics;

        this.focusAlpha += (0.0005f * this.focusDir);
        if(this.focusAlpha > 1.00f) {
            this.focusAlpha = 1.0f;
            this.focusDir = -1.0f;
        }
        if(this.focusAlpha < 0.0f) {
            this.focusAlpha = 0.0f;
            this.focusDir = 1.0f;
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.focusAlpha));

        g2d.setColor(Color.black);
        g2d.fillRect(178, 173, 300, 75);
        g2d.setColor(Color.darkGray);
        g2d.fillRect(170, 165, 300, 75);
        g2d.setColor(Color.white);
        g2d.setFont(this.mainFont);
        g2d.drawString("Click to focus!", 245, 210);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        x /= this.getGameComponent().getDisplayComponent().getScale();
        y /= this.getGameComponent().getDisplayComponent().getScale();
        Rectangle m = new Rectangle(x, y, 2, 2);

        if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 0) {

            if(m.intersects(64, 166, 128, 32)) {
                this.username = "";
                this.getGameComponent().setWorld(new World("Town", Texture.WORLD_TOWN.getImage(), this.getGameComponent()));
                this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(5);
            }
            if(m.intersects(64, 198, 128, 32)) { }
            if(m.intersects(64, 230, 128, 32)) {
                this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(20);
            }
            if(m.intersects(64, 262, 128, 32)) {
                System.out.println("Goodbye!");
                System.exit(0);
            }

        }
        else if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 1) {

            if(m.intersects(64, 358, 96, 32)) this.errorTimer = 0;
            if(m.intersects(64, 230, 96, 32)) {
                this.selectedSkin--;
                if(this.selectedSkin < 0) this.selectedSkin = 0;
            }
            if(m.intersects(166, 230, 96, 32)) {
                this.selectedSkin++;
                if(this.selectedSkin >= 5) this.selectedSkin = 4;
            }
            if(m.intersects(224, 152, 160, 32)) {
                this.gender = !this.gender;
                this.selectedSkin = 0;
            }

        }

        else if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 2) {
            if(m.intersects(64, 166, 128, 32)) this.getGameComponent().setGameState(GameState.Game);
            if(m.intersects(64, 198, 128, 32)) { }
            if(m.intersects(64, 230, 128, 32)) {
                System.exit(0);
            }
        }

        if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 20) {

            if(m.intersects(64, 166, 128, 32)) {
                this.getGameComponent().getDisplayComponent().setControlMainMenuScreen(0);
            }

            if(m.intersects(64, 230, 128, 32)) {
                this.helpPage = 0;
            }
            if(m.intersects(64, 262, 128, 32)) {
                this.helpPage = 1;
            }

        }

    }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

    private void renderCharacterCreation(Graphics graphics) {
        graphics.setFont(this.titleFont);

        graphics.setColor(Color.darkGray);
        graphics.drawString("Character Creation", 72, 72);
        graphics.setColor(Color.white);
        graphics.drawString("Character Creation", 64, 64);

        graphics.setFont(this.mainFont);
        graphics.drawString("Character Name:   ____________________", 32, 128);

        graphics.drawString(this.username, 128+80, 128);
        if(this.cursorBlink && username.length() == 0) {
            graphics.drawString("I", 128+80, 128);
        }

        if(gender) graphics.drawString("Character Gender:    male / FEMALE", 32, 128+48);
        else graphics.drawString("Character Gender:    MALE / female", 32, 128+48);
        graphics.drawString("Character Skin:    " + ( (this.gender) ? this.femaleSkins : this.maleSkins )[this.selectedSkin], 32, 128+96);

        graphics.setFont(this.smallFont);
        graphics.drawString("Previous Skin      Next Skin", 64, 128+128);

        graphics.setFont(this.mainFont);
        if(this.errorTimer == -1) graphics.drawString("CREATE!", 64, 256+128);
        else graphics.drawString("err: 0xA0002000", 64, 256+128);

        graphics.setFont(this.console);

        if(this.errorTimer > 64) {
            graphics.setColor(Color.red);
            graphics.fillRect(64, 128, 256, 96);
            graphics.setColor(Color.black);
            graphics.fillRect(68, 132, 248, 88);
            graphics.setColor(Color.white);
            graphics.drawString("ERR: 0x0A000777", 72, 132+16);
            graphics.drawString("__scary__ is nullptr", 72, 132+32);
            graphics.drawString("read access violation", 72, 132+48);
            this.username = "TheHappyHamburger70#@~*&847%6e5$";
            this.gender = true;
            this.selectedSkin = 2;
        }


        if(this.errorTimer > 128) {
            graphics.setColor(Color.red);
            graphics.fillRect(256, 48, 256, 96);
            graphics.setColor(Color.black);
            graphics.fillRect(260,52, 248, 88);
            graphics.setColor(Color.white);
            graphics.drawString("ERR: 0x00000BF0", 264, 52+16);
            graphics.drawString("CWorld::Add(CPlayer*)", 264, 52+32);
            graphics.drawString("\"*player invalid data\"", 264, 52+48);
            graphics.drawString("player->DeleteBuff(void)", 264, 52+64);
        }

        if(this.errorTimer > 196) {
            graphics.setColor(Color.red);
            graphics.fillRect(224, 320, 256, 96);
            graphics.setColor(Color.black);
            graphics.fillRect(228, 324, 248, 88);
            graphics.setColor(Color.white);
            graphics.drawString("ERR: 0xC0000001", 232, 324+16);
            graphics.drawString("failed to create the", 232, 324+32);
            graphics.drawString("player : using default", 232, 324+48);
            graphics.drawString("profile", 232, 324+64);
        }

    }


    public void keyPressed(KeyEvent e) {
        if(this.getGameComponent().getDisplayComponent().getControlMainMenuScreen() == 1 && this.errorTimer == -1) {
            char c = e.getKeyChar();

            if(Character.isLetter(c) || Character.isDigit(c) || c == ' ') {
                if(this.username.length() < 20) this.username += c;
            }
            if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && this.username.length() > 0) this.username = this.username.substring(0, this.username.length() - 1);

        }
    }

    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }

    private void renderPause(Graphics graphics) {
        graphics.setFont(this.titleFont);

        graphics.setColor(Color.darkGray);
        graphics.drawString("Game Paused", 72, 72);
        graphics.setColor(Color.white);
        graphics.drawString("Game Paused", 64, 64);

        graphics.setFont(this.mainFont);
        graphics.drawString("Resume", 64, 128+64);
        // graphics.drawString("Options", 64, 128+64+32);
        graphics.drawString("Quit", 64, 256);

    }

    private void renderCutscene(Graphics graphics) {
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setColor(Color.black);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));

        switch(this.scene) {

            case 0:
                g2d.drawImage(Texture.STORY01.getImage(), 0, 0, 640, 480, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                g2d.fillRect(64, 300, 640-128, 128);
                g2d.setColor(Color.white);
                g2d.drawString("A tower that pierces the night skies, is the home to a corporation that has a monopoly", 72, 316);
                g2d.drawString("over a city with a population of 5 million.", 72, 332);
                g2d.drawString("Their latest product, that was in development for around 3 years now, is called Inferis", 72, 364);
                g2d.drawString("Project, a VR RPG game.", 72, 380);
                break;

            case 1:
                g2d.drawImage(Texture.STORY02.getImage(), 0, 0, 640, 480, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                g2d.fillRect(64, 300, 640-128, 128);
                g2d.setColor(Color.white);
                g2d.drawString("With their public showcase held a week ago, I, Natalie Iris", 72, 316);
                g2d.drawString("was hired as a developer to fix \"some minor problems related to performance\". An ordinary", 72, 332);
                g2d.drawString("job I thought.", 72, 348);
                g2d.drawString("", 72, 364);
                break;

            case 2:
                g2d.drawImage(Texture.STORY03.getImage(), 0, 0, 640, 480, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                g2d.fillRect(64, 300, 640-128, 128);
                g2d.setColor(Color.white);
                g2d.drawString("A really easy task... Wrong! Turns out that the game was developed by a 17 year old", 72, 316);
                g2d.drawString("kid in his' mom's basement. The problem is that the kid knows nothing about", 72, 332);
                g2d.drawString("programming, he simply watched video tutorials, thinking he can make the next", 72, 348);
                g2d.drawString("masterpiece...", 72, 364);
                g2d.drawString("The game runs in 5 fps, the source code is so bad that even a quantum supercomputer", 72, 380);
                g2d.drawString("couldn't fix it. ... And, it was my job to fix it.", 72, 396);
                break;

            case 3:
                g2d.drawImage(Texture.STORY04.getImage(), 0, 0, 640, 480, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                g2d.fillRect(64, 300, 640-128, 128);
                g2d.setColor(Color.white);
                g2d.drawString("Even worse, the game was written in this old obscure engine, where the only", 72, 316);
                g2d.drawString("way to fix these bugs would be to go into the game...", 72, 332);
                g2d.drawString("", 72, 348);
                g2d.drawString("... what could possibly go wrong ?", 72, 364);
                break;
        }

    }

    public void renderVictory(Graphics graphics) {
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setColor(Color.black);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
        g2d.drawImage(Texture.STORY_WIN.getImage(), 0, 0, 640, 480, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
        g2d.fillRect(64, 300, 640-128, 128);
        g2d.setColor(Color.white);
        g2d.drawString("I did it!", 72, 316);
        g2d.drawString("I finished the job successfully, and was paid 200000 EUR.", 72, 332);
        g2d.drawString("I wonder what I'll be able to do with that much money....", 72, 348);
        g2d.drawString("** GOOD ENDING **", 72, 364);
    }

    public void renderDefeat(Graphics graphics) {
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setColor(Color.black);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
        g2d.drawImage(Texture.STORY_DEFEAT.getImage(), 0, 0, 640, 480, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
        g2d.fillRect(64, 300, 640-128, 128);
        g2d.setColor(Color.white);
        g2d.drawString("I failed to do it... maybe this job was not meant for me.", 72, 316);
        g2d.drawString("Some good news, the CEO sued me for failing to complete a task and I've lost everything", 72, 332);
        g2d.drawString("I had. Now I'm living under a bridge...", 72, 348);
        g2d.drawString("** BAD ENDING **", 72, 364);
    }

    public void renderVoidptr(Graphics graphics) {
        graphics.drawImage(Texture.VOID_POINTER_LOGO.getImage(), 245, 100, null);
        graphics.drawString("Thank you for playing", 260, 316);
    }

    public void renderAbout(Graphics graphics) {
        graphics.setFont(this.titleFont);

        // graphics.drawImage(Texture.MAIN_MENU_BANNER.getImage(), 0, 0, 640 ,480, null);

        graphics.setColor(Color.darkGray);
        graphics.drawString("Inferis Project", 72, 72);
        graphics.setColor(Color.white);
        graphics.drawString("Inferis Project", 64, 64);

        graphics.setFont(this.mainFont);
        graphics.drawString("Return", 64, 128+64);
        // graphics.drawString("Options", 64, 128+64+32);
        graphics.drawString("About", 64, 256);
        graphics.drawString("Help", 64, 256+32);

        graphics.setFont(this.smallFont);
        if(this.helpPage == 0) {
            graphics.drawString("Inferis Project was created for Ludum Dare 49, a 48 hour", 128+64, 256-128);
            graphics.drawString("game jam.", 128+64, 256-128+24);

            graphics.drawString("Created by Aljosa. A. \"v0idpointer\", October 2021.", 128+64, 256-128+24+48);
            graphics.drawString("Twitter: @v0idpointer", 128+64, 256-128+48+48);
            graphics.drawString("YouTube: v0idpointer", 128+64, 256-128+48+48 + 24);
            graphics.drawString("Steam: V0idPointer", 128+64, 256-128+48+48 + 48);
        }
        else {
            graphics.drawString("Use W,A,S,D to move around.", 128+64, 256-128);
            graphics.drawString("Use SPACE to attack. You can use Q to switch", 128+64, 256-128+24);
            graphics.drawString("between ranged and melee attacks.", 128+64, 256-128+48);
            graphics.drawString("Press and hold M to open the map. Use the map to", 128+64, 256-128+24+48);
            graphics.drawString("search for objectives. Attack opponents or use", 128+64, 256-128+48+48);
            graphics.drawString("crystals to destroy Visual or Memory leaks. Press", 128+64, 256-128+48+48 + 24);
            graphics.drawString("F when near a Leak to fix it.", 128+64, 256-128+48+48 + 48);
            graphics.drawString("The HUD will display your health and weapon", 128+64, 256-128+48+48 + 48 + 24);
            graphics.drawString("charge progress. You can hold down SPACE to", 128+64, 256-128+48+48 + 48 + 48);
            graphics.drawString("charge your weapons, in order to perform powerful", 128+64, 256-128+48+48 + 48 + 48+24);
            graphics.drawString("attacks.", 128+64, 256-128+48+48 + 48 + 48+48);

        }

        graphics.setFont(this.smallFont);
        graphics.drawString(Main.VERSION, 32, 420);

    }


}
