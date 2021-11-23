package jw05.anish.calabashbros;

import java.awt.Color;
import jw05.anish.algorithm.Tuple;

public class Thing {

    protected World world;

    public Tile<? extends Thing> tile;

    public Tuple<Integer, Integer> getPos() {
        return this.tile.getPos();
    }

    public void setPos(Tuple<Integer, Integer> pos){
        this.tile.setPos(pos);
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    Thing(Color color, char glyph, World world) {
        this.color = color;
        this.glyph = glyph;
        this.world = world;
    }

    private Color color;

    public Color getColor() {
        return this.color;
    }

    public void changeColor(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    private final char glyph;

    public char getGlyph() {
        return this.glyph;
    }

}
