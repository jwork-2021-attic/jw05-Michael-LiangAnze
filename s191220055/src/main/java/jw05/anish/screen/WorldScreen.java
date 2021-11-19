package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import jw05.anish.calabashbros.Calabash;
import jw05.anish.calabashbros.World;
import jw05.anish.calabashbros.Wall;

import jw05.asciiPanel.AsciiPanel;
import jw05.anish.map.Map;
public class WorldScreen implements Screen {

    private World world;
    Calabash calabash;
    Map map;
    int mapSize;

    public WorldScreen() {
        world = new World();
        mapSize = world.getWorldSize();
        generateMyMap();
        calabash = new Calabash(new Color(240, 240, 0), 1, world); // original place
        world.put(new Calabash(new Color(255, 0, 0), 1, world), 28, 1);
        world.put(calabash, 1, 1);

        //启动刷新线程
        Thread t = new Thread(calabash);
        t.start();
    }

    private void generateMyMap()  {
        try{
            map = new Map(mapSize, "src/main/java/jw05/anish/map/map.txt");
            map.loadMap();
            // M.outputMap();
            int[][] tempMap = map.getMap();
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    if (tempMap[j][i] == 1) {
                        world.put(new Wall(this.world), i, j);
                    }
                }
            }
        }
        catch(IOException e){
            System.out.format("map file not found\n");
            assert false:"map file not found\n";
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
        //     this.execute(steps[i], steps[i - 1]);
        //     i++;
        // }
        // calabash.moveTo(2,4);
        // i++;
        return this;
    }
}
