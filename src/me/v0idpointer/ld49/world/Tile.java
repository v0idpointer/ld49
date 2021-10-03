package me.v0idpointer.ld49.world;

import me.v0idpointer.ld49.gfx.Texture;

public class Tile {

    public static Tile[] tiles = new Tile[64];

    public static Tile grassTile = new Tile(1, "grass", Texture.GRASS_TEXTURE);
    public static Tile pathTile = new Tile(2, "path", Texture.PATH_TEXTURE);
    public static Tile waterTile = new Tile(3, "water", Texture.WATER_TEXTURE);
    public static Tile cobblestone = new Tile(4, "cobblestone", Texture.COBBLESTONE);
    public static Tile smoothStone = new Tile(5, "smooth stone", Texture.SMOOTH_STONE);
    public static Tile nullGrassTile = new Tile(6, "null grass", Texture.NULL_GRASS);

    private final String name;
    private final int id;
    private final Texture texture;

    public Tile(int id, String name, Texture texture) {
        this.id = id;
        this.name = name;
        this.texture = texture;

        if(tiles[id] != null) throw new RuntimeException("Tile with the ID of " + id + " is already registered!");
        tiles[id] = this;
    }

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getId() {
        return id;
    }

    public static int GetRegisteredTiles() {
        int c = 0;
        for(Tile t : tiles) if(t != null) c++;
        return c;
    }

}
