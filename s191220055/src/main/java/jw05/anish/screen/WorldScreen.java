package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import jw05.anish.calabashbros.Calabash;
import jw05.anish.calabashbros.Monster;
import jw05.anish.calabashbros.World;
import jw05.anish.calabashbros.Wall;
import java.util.ArrayList;
import jw05.asciiPanel.AsciiPanel;
import jw05.anish.map.Map;
import jw05.anish.algorithm.Tuple;
public class WorldScreen implements Screen {

    private World world;
    Map map;
    int mapSize;
    ArrayList<Tuple<Calabash,Integer>> calabashList;

    public WorldScreen() {
        calabashList = new ArrayList<Tuple<Calabash,Integer>>();
        world = new World();
        mapSize = world.getWorldSize();
        generateMyMap();
        Monster m = new Monster(new Color(255, 0, 0), 1,world,map,calabashList); // original place
        Calabash c = new Calabash(new Color(255, 240, 0), 1, world);
        world.put(m, 1, 1);
        world.put(c, 28, 1);
        calabashList.add(new Tuple<Calabash,Integer>(c,-1));
        // 启动刷新线程
        Thread t = new Thread(m);
        t.start();
    }

    private void generateMyMap() {
        try {
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
