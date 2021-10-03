package me.v0idpointer.ld49.world;

import me.v0idpointer.ld49.Game;
import me.v0idpointer.ld49.entity.*;
import me.v0idpointer.ld49.gfx.Texture;
import me.v0idpointer.ld49.input.KeyboardInput;

import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    public static int[] COLOR_REF = new int[0xFFFFFF];

    public static void CreateColorRef() {
        COLOR_REF[0x00FF00] = 1; // Grass tile
        COLOR_REF[0xA09040] = 2; // Grass Path
        COLOR_REF[0x0000FF] = 3; // Water
        COLOR_REF[0x404040] = 4; // Cobblestone
        COLOR_REF[0x808080] = 5; // Smooth Stone
        COLOR_REF[0xFF0000] = 6;
    }

    private String worldName;
    private BufferedImage tileset;
    private CopyOnWriteArrayList<Entity> entities;
    private final Game gameComponent;

    private int objectiveDestroyAnomalies = 0; // max 8
    private int objectiveDestroyNullMen = 0; // max 4
    private int objectiveStopMemoryLeak = 0; // max 1
    private int objectiveStopVisualLeak = 0; // max 2

    private int utilityHealCrystal = 5;
    private int utilityVisualCrystal = 2;
    private int utilityMemoryCrystal = 1;

    private boolean showTutorial = true;
    private boolean init = true;

    public World(String worldName, BufferedImage tileset, Game gameComponent) {
        this.tileset = tileset;
        this.worldName = worldName;
        this.gameComponent = gameComponent;
        this.entities = new CopyOnWriteArrayList<>();
        this.entities.add(new Player(2112, 2816, this.gameComponent));

        this.entities.add(new PropStatic(2400, 2464, this.gameComponent, Texture.PROP_HOUSE01, 256, 256));
        this.entities.add(new PropStatic(1732, 2464, this.gameComponent, Texture.PROP_HOUSE02, 196, 256));
        this.entities.add(new PropStatic(1600, 2100, this.gameComponent, Texture.PROP_HOUSE01, 192, 192));
        this.entities.add(new PropStatic(1856, 2100, this.gameComponent, Texture.PROP_HOUSE04, 192, 192));
        this.entities.add(new PropStatic(1400, 2364, this.gameComponent, Texture.PROP_HOUSE03, 256, 256));
        this.entities.add(new PropStatic(960, 2464, this.gameComponent, Texture.PROP_HOUSE04, 256, 256));
        this.entities.add(new PropStatic(960, 2900, this.gameComponent, Texture.PROP_HOUSE05, 256, 256));
        this.entities.add(new PropStatic(1400, 3000, this.gameComponent, Texture.PROP_HOUSE01, 256, 256));
        this.entities.add(new PropStatic(1664, 3332, this.gameComponent, Texture.PROP_HOUSE03, 256, 256));
        this.entities.add(new PropStatic(2400, 3000, this.gameComponent, Texture.PROP_HOUSE05, 256, 256));
        this.entities.add(new PropStatic(1832, 1700, this.gameComponent, Texture.PROP_HOUSE02, 192, 192));
        this.entities.add(new PropStatic(2300, 1700, this.gameComponent, Texture.PROP_HOUSE04, 192, 192));
        this.entities.add(new PropStatic(2364, 2096, this.gameComponent, Texture.PROP_HOUSE02, 256, 256));
        this.entities.add(new PropStatic(3064, 2464, this.gameComponent, Texture.PROP_HOUSE05, 256, 256));
        this.entities.add(new PropStatic(3064, 2932, this.gameComponent, Texture.PROP_HOUSE01, 256, 256));
        this.entities.add(new PropStatic(2232, 3772, this.gameComponent, Texture.PROP_HOUSE02, 256, 256));
        this.entities.add(new PropStatic(1800, 3772, this.gameComponent, Texture.PROP_HOUSE04, 256, 256));

    }

    public void update() {
        for(Entity entity : this.entities) {
            entity.update();

            if(entity.getX() < 0) entity.setX(0);
            if(entity.getY() < 0) entity.setY(0);
        }
    }

    public BufferedImage getTileset() {
        return tileset;
    }

    public void setTileset(BufferedImage tileset) {
        this.tileset = tileset;
    }

    public CopyOnWriteArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(CopyOnWriteArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Player getPlayer() {
        Player player = null;

        for(Entity entity : this.entities) {
            if(entity instanceof Player) player = (Player)entity;
        }

        return player;
    }

    public int getObjectiveDestroyAnomalies() {
        return objectiveDestroyAnomalies;
    }

    public void setObjectiveDestroyAnomalies(int objectiveDestroyAnomalies) {
        this.objectiveDestroyAnomalies = objectiveDestroyAnomalies;
    }

    public int getObjectiveDestroyNullMen() {
        return objectiveDestroyNullMen;
    }

    public void setObjectiveDestroyNullMen(int objectiveDestroyNullMen) {
        this.objectiveDestroyNullMen = objectiveDestroyNullMen;
    }

    public int getObjectiveStopMemoryLeak() {
        return objectiveStopMemoryLeak;
    }

    public void setObjectiveStopMemoryLeak(int objectiveStopMemoryLeak) {
        this.objectiveStopMemoryLeak = objectiveStopMemoryLeak;
    }

    public int getObjectiveStopVisualLeak() {
        return objectiveStopVisualLeak;
    }

    public void setObjectiveStopVisualLeak(int objectiveStopVisualLeak) {
        this.objectiveStopVisualLeak = objectiveStopVisualLeak;
    }

    public boolean checkForCompletion() {
        if(this.getObjectiveDestroyAnomalies() >= 8 && this.getObjectiveDestroyNullMen() >= 4 && this.getObjectiveStopMemoryLeak() >= 1 && this.getObjectiveStopVisualLeak() >= 2)
            return true;
        else return false;
    }

    public int getUtilityHealCrystal() {
        return utilityHealCrystal;
    }

    public void setUtilityHealCrystal(int utilityHealCrystal) {
        this.utilityHealCrystal = utilityHealCrystal;
    }

    public int getUtilityVisualCrystal() {
        return utilityVisualCrystal;
    }

    public void setUtilityVisualCrystal(int utilityVisualCrystal) {
        this.utilityVisualCrystal = utilityVisualCrystal;
    }

    public int getUtilityMemoryCrystal() {
        return utilityMemoryCrystal;
    }

    public void setUtilityMemoryCrystal(int utilityMemoryCrystal) {
        this.utilityMemoryCrystal = utilityMemoryCrystal;
    }

    public boolean isShowTutorial() {
        return showTutorial;
    }

    public void setShowTutorial(boolean showTutorial) {
        this.showTutorial = showTutorial;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
}
