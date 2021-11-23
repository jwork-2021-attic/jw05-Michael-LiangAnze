package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jw05.anish.calabashbros.Player;
import jw05.anish.calabashbros.Shooter;
import jw05.anish.calabashbros.SworksMan;
import jw05.anish.calabashbros.CannonballList;
import jw05.anish.calabashbros.World;
import jw05.anish.calabashbros.Wall;
import jw05.asciiPanel.AsciiPanel;
import jw05.anish.map.Map;


public class WorldScreen implements Screen {

    private World world;
    private Map map;
    private int mapSize;
    private Player player;
    private CannonballList cannonballList;
    private ExecutorService exec;

    public WorldScreen() {
        world = new World();
        mapSize = world.getWorldSize();
        world.setWorldState(0);
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
    public void rulesScreen() {
        world.setRulesWorld();
    }

    @Override
    public void startScreen() {
        world.setGameWorld();
        mapSize = world.getWorldSize();
        generateMyMap();

        // 设置地图
        map.setMoveable(28,1,1);
        map.setMoveable(1,1,1);
        map.setMoveable(2,2,1);
        
        //创建人物
        player = new Player(new Color(0, 255, 0), 1,300,3,world,map);
        cannonballList = new CannonballList(1,400,player,map,world);
        Shooter shooter1 = new Shooter(1, 200, 4,world, map, player,cannonballList,25,1,28,3);
        SworksMan sworksMan1 = new SworksMan(1, 100, 2,1, 100, world, map, player,1,1,3,3);

        // 放入世界
        world.put(player, 2, 2);
        world.put(shooter1, 28, 1);
        world.put(sworksMan1, 1, 1);

        //设置线程
        Thread t1 = new Thread(player);
        Thread t2 = new Thread(shooter1);
        Thread t3 = new Thread(sworksMan1);
        Thread t4 = new Thread(cannonballList);

        // 启动线程
        exec = Executors.newCachedThreadPool();
        exec.execute(t1);
        exec.execute(t2);
        exec.execute(t3);
        exec.execute(t4);
    }

    @Override
    public void gameOverScreen() {
        world.setGameOverWorld();
    }

    @Override
    public void updateScreenState() {
        if(this.player != null){
            if(!player.isAlive()){
                world.setWorldState(2);
            }
        }
    }

    @Override
    public int getScreenState() {
        return world.getWorldState();
    }

    @Override
    public ExecutorService getThreadPool() {
        return exec;
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
        if(world.getWorldState() == 0){ //开始界面
            if(key.getKeyCode() == KeyEvent.VK_ENTER){
                world.setWorldState(1);
                startScreen();
            }
        }
        else{
            switch(key.getKeyCode()){
                case KeyEvent.VK_UP:player.movePlayer(2);break;
                case KeyEvent.VK_DOWN:player.movePlayer(1);break;
                case KeyEvent.VK_LEFT:player.movePlayer(3);break;
                case KeyEvent.VK_RIGHT:player.movePlayer(4);break;
            }
        }
        return this;
    }
}
