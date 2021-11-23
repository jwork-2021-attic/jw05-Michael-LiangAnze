package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import jw05.anish.calabashbros.Player;
import jw05.anish.calabashbros.Shooter;
import jw05.anish.calabashbros.SworksMan;
import jw05.anish.calabashbros.Cannonball;
import jw05.anish.calabashbros.CannonballList;
import jw05.anish.calabashbros.World;
import jw05.anish.calabashbros.Wall;
import jw05.asciiPanel.AsciiPanel;
import jw05.anish.map.Map;


public class WorldScreen implements Screen {

    private World world;
    Map map;
    int mapSize;
    Player player;
    CannonballList cannonballList;
    
    public WorldScreen() {
        world = new World();
        mapSize = world.getWorldSize();
        generateMyMap();

        // 设置地图
        map.setMoveable(6,1,1);
        map.setMoveable(6,2,1);
        // map.setMoveable(7,10,1);
        
        //创建人物
        player = new Player(new Color(0, 255, 0), 1,300,6,world,map);
        cannonballList = new CannonballList(1,400,player,map,world);
        Shooter shooter1 = new Shooter(1, 100, 4, 100,world, map, player,cannonballList);
        // SworksMan sworksMan1 = new SworksMan(1, 100, 4,1, 100, world, map, player,3,7,9,13);

        // 放入世界
        world.put(player, 6, 1);
        world.put(shooter1, 6, 2);
        // world.put(sworksMan1, 7, 10);



        //设置线程
        Thread t1 = new Thread(player);
        Thread t2 = new Thread(shooter1);
        // Thread t3 = new Thread(sworksMan1);
        Thread t4 = new Thread(cannonballList);

        // 启动线程
        t1.start();
        t2.start();
        // t3.start();
        t4.start();
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

    @Override
    public Screen releaseKey(){
        player.resetDirection();
        return this;
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch(key.getKeyCode()){
            case KeyEvent.VK_UP:player.movePlayer(2);break;
            case KeyEvent.VK_DOWN:player.movePlayer(1);break;
            case KeyEvent.VK_LEFT:player.movePlayer(3);break;
            case KeyEvent.VK_RIGHT:player.movePlayer(4);break;
        }
        // System.out.println(key.getKeyCode());
        return this;
    }
}
