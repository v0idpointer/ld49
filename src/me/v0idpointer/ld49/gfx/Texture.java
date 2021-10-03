package me.v0idpointer.ld49.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {

    public static float displayScale = 0;
    public static final Texture VOID_POINTER_LOGO = new Texture("voidptr");

    public static Texture GRASS_TEXTURE = new Texture("grass");
    public static Texture PATH_TEXTURE  = new Texture("path");
    public static Texture GRASS_LEFT_SIDE = new Texture("grass_left_side");
    public static Texture GRASS_RIGHT_SIDE = new Texture("grass_right_side");
    public static Texture GRASS_UP_SIDE = new Texture("grass_side_up");
    public static Texture GRASS_BOTTOM_SIDE = new Texture("grass_side_bottom");

    public static Texture UI_DUAL_BLADES = new Texture("ui/dual_blades");
    public static Texture UI_BOW_N_ARROW = new Texture("ui/bow_and_arrow");

    public static Texture CHAR_MAIDEN_FRONT = new Texture("char/maid_front");
    public static Texture CHAR_MAIDEN_BACK = new Texture("char/maid_back");
    public static Texture CHAR_MAIDEN_RIGHT = new Texture("char/maid_right");
    public static Texture CHAR_MAIDEN_LEFT = new Texture("char/maid_left");

    public static Texture WEAPON_SWORDS_FRONT = new Texture("weapon_model/swords_front");
    public static Texture WEAPON_SWORDS_BACK = new Texture("weapon_model/swords_back");
    public static Texture WEAPON_SWORDS_RIGHT = new Texture("weapon_model/swords_right");
    public static Texture WEAPON_SWORDS_LEFT = new Texture("weapon_model/swords_left");

    public static Texture WEAPON_BOW_FRONT = new Texture("weapon_model/bow_front");
    public static Texture WEAPON_BOW_BACK = new Texture("weapon_model/bow_back");
    public static Texture WEAPON_BOW_RIGHT = new Texture("weapon_model/bow_right");
    public static Texture WEAPON_BOW_LEFT = new Texture("weapon_model/bow_left");

    public static Texture ARROW_SPRITE_SHEET = new Texture("weapon_model/arrow_sheet");
    public static Texture SLIME_SPRITE_SHEET = new Texture("enemy/slime");

    public static Texture PROP_FENCE = new Texture("prop/fence");
    public static Texture PROP_FENCE_RIGHT = new Texture("prop/fence_right");
    public static Texture PROP_FENCE_LEFT = new Texture("prop/fence_left");
    public static Texture PROP_SIGN = new Texture("prop/sign");
    public static Texture PROP_SIGN_WRITTEN = new Texture("prop/sign_written");
    public static Texture PROP_SIGN_DIRECTIONAL = new Texture("prop/sign_direction");

    public static Texture DEFAULT = new Texture("default");

    public static Texture ANOMALY_SLIME_SPRITE_SHEET = new Texture("enemy/anomaly_slime");

    public static Texture WATER_TEXTURE = new Texture("water01");

    public static Texture COBBLESTONE = new Texture("cobblestone");
    public static Texture SMOOTH_STONE = new Texture("smooth_stone");

    public static Texture PROP_HOUSE01 = new Texture("prop/house_01");
    public static Texture PROP_HOUSE02 = new Texture("prop/house_02");
    public static Texture PROP_HOUSE03 = new Texture("prop/house_03");
    public static Texture PROP_HOUSE04 = new Texture("prop/house_04");
    public static Texture PROP_HOUSE05 = new Texture("prop/house_05");

    public static Texture PROP_PURPLE_TREE = new Texture("prop/prop_purple_tree");
    public static Texture PROP_WALL = new Texture("prop/wall");
    public static Texture PROP_WALL_VERTICAL = new Texture("prop/wall_vert");
    public static Texture PROP_WALL_CONNECTION = new Texture("prop/wall_conn");

    public static Texture HEAL_CRYSTAL = new Texture("weapon_model/heal_crystal");
    public static Texture MEMORY_CRYSTAL = new Texture("weapon_model/mem_crystal");
    public static Texture VISUAL_CRYSTAL = new Texture("weapon_model/leak_crystal");

    public static Texture NULL_GRASS = new Texture("null_grass");
    public static Texture NULL_GRASS_TOP = new Texture("null_grass_top");
    public static Texture NULL_GRASS_BOTTOM = new Texture("null_grass_bottom");
    public static Texture NULL_GRASS_LEFT = new Texture("null_grass_left");
    public static Texture NULL_GRASS_RIGHT = new Texture("null_grass_right");

    public static Texture ANOMALY_MEMORY_LEAK = new Texture("enemy/memory_leak");
    public static Texture ANOMALY_VISUAL_LEAK = new Texture("enemy/vis_leak");

    public static Texture ANOMALY_NULL_MAN = new Texture("enemy/nullman");

    public static final Texture WORLD_TOWN = new Texture("worlds/town");
    public static final Texture MAIN_MENU_BANNER = new Texture("banner");
    public static final Texture UX_MAP = new Texture("map");
    public static final Texture SETUP01 = new Texture("setup01");

    public static final Texture STORY01 = new Texture("storyboard/story01");
    public static final Texture STORY02 = new Texture("storyboard/story02");
    public static final Texture STORY03 = new Texture("storyboard/story03");
    public static final Texture STORY_WIN = new Texture("storyboard/story_win");
    public static final Texture STORY_DEFEAT = new Texture("storyboard/story_defeat");
    public static final Texture STORY04 = new Texture("storyboard/story04");

    public static final Texture MAID_WALK_FORWARD = new Texture("char/maid_walk_front");
    public static final Texture MAID_WALK_BACK = new Texture("char/maid_walk_back");
    public static final Texture MAID_WALK_RIGHT = new Texture("char/maid_walk_right");
    public static final Texture MAID_WALK_LEFT = new Texture("char/maid_walk_left");

    private final String name;
    private final BufferedImage image;

    public Texture(String name) {
        this.name = name;

        BufferedImage temp;
        try {
            temp = ImageIO.read(this.getClass().getResourceAsStream("/textures/" + name + ".png"));
            System.out.println("Successfully loaded /textures/" + name + ".");
        }
        catch(IOException ex) {
            temp = null;
            ex.printStackTrace();
            System.out.println("Err: " + ex.getLocalizedMessage());
        }
        this.image = temp;

    }

    public String getName() {
        return this.name;
    }

    public BufferedImage getImage() {
        return this.image;
    }

}
