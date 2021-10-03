package me.v0idpointer.ld49.impressive;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.GameState;
import me.v0idpointer.ld49.gfx.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundRender extends Impressive {

    private float crosswordFade = 0.00f;

    public BackgroundRender(Game gameComponent) {
        super("background_render", gameComponent);
    }

    @Override
    public void render() {
        BufferedImage image = new BufferedImage(DisplayComponent.WIDTH, DisplayComponent.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        if(this.getGameComponent().getGameState() == GameState.StartupScreen) this.renderStartupScreen(graphics);
        else if(this.getGameComponent().getGameState() == GameState.PostStartLoading) this.renderPostStartupLoadingScreen(graphics);

        this.setNextBuffer(image);
        this.markBufferForSwap();
    }

    @Override
    public void update() { }

    public void renderStartupScreen(Graphics graphics) {
        if(this.crosswordFade != 1.00f) this.crosswordFade += 0.0005f;
        if(this.crosswordFade > 1.00f) this.crosswordFade = 1.00f;

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.crosswordFade));
        g2d.drawImage(Texture.VOID_POINTER_LOGO.getImage(), 245, 165, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
    }

    public void renderPostStartupLoadingScreen(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString("Please wait while the game loads all required resources...", 64, 64);
    }

}
