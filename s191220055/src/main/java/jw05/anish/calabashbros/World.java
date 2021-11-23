package jw05.anish.calabashbros;

import jw05.anish.algorithm.Tuple;

public class World {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    
    private Tile<Thing>[][] tiles;

    @SuppressWarnings(value = "all")
    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {   
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this));
            }
        }

    }
    public int getWorldSize(){
        return HEIGHT;
    }
    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public void swapPos(Tuple<Integer,Integer>p1,Tuple<Integer,Integer>p2){
        Thing t1 = get(p1.first,p1.second);
        Thing t2 = get(p2.first,p2.second);
        put(t2,p1.first,p1.second);
        put(t1,p2.first,p2.second);
    }
}
