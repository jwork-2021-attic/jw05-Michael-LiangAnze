package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import jw05.anish.calabashbros.Calabash;
import jw05.anish.calabashbros.Bomber;
import jw05.anish.calabashbros.World;
import jw05.anish.calabashbros.Wall;
import jw05.asciiPanel.AsciiPanel;
import jw05.anish.map.Map;


public class WorldScreen implements Screen {

    private World world;
    Map map;
    int mapSize;

    public WorldScreen() {
        world = new World();
        mapSize = world.getWorldSize();
        generateMyMap();
        Calabash calabash1 = new Calabash(new Color(255, 240, 0), 1,100, world,map);
        Bomber monster1 = new Bomber(new Color(255, 0, 0), 1, 100, 4, world, map, calabash1);
 
        

        world.put(monster1, 6, 5);
        world.put(calabash1, 6, 1);

        map.setMoveable(3,1,1);
        map.setMoveable(1,1,1);

        Thread t1 = new Thread(calabash1);
        Thread t2 = new Thread(monster1);
        
        t2.start();
        t1.start();
    }

    private void generateMyMap() {
        try {
            map = new Map(mapSize, "src/main/java/jw05/anish/map/map.txt");
            map.loadMap();
            int[][] tempMap = new int[mapSize][mapSize];
            map.getMapState(tempMap);
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    if (tempMap[i][j] == 1) {
                        world.put(new Wall(this.world), i, j);
                    }
                }
            }
        } catch (IOException e) {
            System.out.format("map file not found\n");
            assert false : "map file not found\n";
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
            }
        }
    }

    int i = 1;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        // if (i < this.steps.length) {
        // this.execute(steps[i], steps[i - 1]);
        // i++;
        // }
        // calabash.moveTo(2,4);
        // i++;
        return this;
    }
}
