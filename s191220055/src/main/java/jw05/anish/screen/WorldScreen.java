package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jw05.anish.calabashbros.Player;
import jw05.anish.calabashbros.Shooter;
import jw05.anish.calabashbros.SworksMan;
import jw05.anish.algorithm.Tuple;
import jw05.anish.calabashbros.Reward;
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
            map = new Map(mapSize, "src/main/java/jw05/anish/map/map.txt",world);
            map.loadMap();
            int[][] tempMap = new int[mapSize][mapSize];
            map.getMapState(tempMap);
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    if (tempMap[i][j] == 1) {
                        world.put(new Wall(this.world), new Tuple<Integer,Integer>(i,j));
                    }
                }
            }
        } catch (IOException e) {
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
  
        // 创建人物和道具
        player = new Player(new Color(96, 240, 100), 1, 500, 4, world, map);
        cannonballList = new CannonballList(1, 400, player, map, world);
        Shooter shooter1 = new Shooter(1, 100, 4, world, map, player, cannonballList, 3, 1, 14, 3);
        Shooter shooter2 = new Shooter(1, 100, 4, world, map, player, cannonballList, 7, 37, 13, 38);
        Shooter shooter3 = new Shooter(1, 100, 4, world, map, player, cannonballList, 13, 14, 16, 18);
        Shooter shooter4 = new Shooter(1, 100, 4, world, map, player, cannonballList, 22, 3, 27, 5);
        Shooter shooter5 = new Shooter(1, 100, 4, world, map, player, cannonballList, 26, 35, 38, 38);

        SworksMan sworksMan1 = new SworksMan(1, 100, 4,1, 100, world, map,player,1,11,11,18);
        SworksMan sworksMan2 = new SworksMan(1, 100, 4,1, 100, world, map,player,1,22,11,28);
        SworksMan sworksMan3 = new SworksMan(1, 100, 4,1, 100, world, map,player,8,29,15,35);
        SworksMan sworksMan4 = new SworksMan(1, 100, 4,1, 100, world, map,player,11,22,24,28);
        SworksMan sworksMan5 = new SworksMan(1, 100, 5,1, 100, world, map,player,24,9,35,16);
        SworksMan sworksMan6 = new SworksMan(1, 100, 4,1, 100, world, map,player,18,18,26,24);
        SworksMan sworksMan7 = new SworksMan(1, 100, 4,1, 100, world, map,player,15,29,24,38);
        SworksMan sworksMan8 = new SworksMan(1, 100, 4,1, 100, world, map,player,26,22,38,31);

        Reward bonus1 = new Reward(world);
        Reward bonus2 = new Reward(world);
        Reward bonus3 = new Reward(world);
        Reward bonus4 = new Reward(world);

        //创建位置
        Tuple<Integer,Integer> playerPos = new Tuple<Integer,Integer>(1,1);

        Tuple<Integer,Integer> shooter1Pos = new Tuple<Integer,Integer>(9,3);
        Tuple<Integer,Integer> shooter2Pos = new Tuple<Integer,Integer>(8,37);
        Tuple<Integer,Integer> shooter3Pos = new Tuple<Integer,Integer>(15,16);
        Tuple<Integer,Integer> shooter4Pos = new Tuple<Integer,Integer>(24,4);
        Tuple<Integer,Integer> shooter5Pos = new Tuple<Integer,Integer>(31,37);

        Tuple<Integer,Integer> sworksMan1Pos = new Tuple<Integer,Integer>(4,13);
        Tuple<Integer,Integer> sworksMan2Pos = new Tuple<Integer,Integer>(6,25);
        Tuple<Integer,Integer> sworksMan3Pos = new Tuple<Integer,Integer>(10,32);
        Tuple<Integer,Integer> sworksMan4Pos = new Tuple<Integer,Integer>(15,25);
        Tuple<Integer,Integer> sworksMan5Pos = new Tuple<Integer,Integer>(28,12);
        Tuple<Integer,Integer> sworksMan6Pos = new Tuple<Integer,Integer>(22,21);
        Tuple<Integer,Integer> sworksMan7Pos = new Tuple<Integer,Integer>(18,34);
        Tuple<Integer,Integer> sworksMan8Pos = new Tuple<Integer,Integer>(32,28);

        Tuple<Integer,Integer> reward1Pos = new Tuple<Integer,Integer>(13,12);
        Tuple<Integer,Integer> reward2Pos = new Tuple<Integer,Integer>(9,1);
        Tuple<Integer,Integer> reward3Pos = new Tuple<Integer,Integer>(38,8);
        Tuple<Integer,Integer> reward4Pos = new Tuple<Integer,Integer>(15,35);


        // 设置地图,是否需要和上一部分互换位置？
        map.setMoveable(playerPos, 1);
        map.setMoveable(shooter1Pos, 1);
        map.setMoveable(shooter2Pos, 1);
        map.setMoveable(shooter3Pos, 1);
        map.setMoveable(shooter4Pos, 1);
        map.setMoveable(shooter5Pos, 1);

        map.setMoveable(sworksMan1Pos,1);
        map.setMoveable(sworksMan2Pos,1);
        map.setMoveable(sworksMan3Pos,1);
        map.setMoveable(sworksMan4Pos,1);
        map.setMoveable(sworksMan5Pos,1);
        map.setMoveable(sworksMan6Pos,1);
        map.setMoveable(sworksMan7Pos,1);
        map.setMoveable(sworksMan8Pos,1);

        map.setMoveable(reward1Pos,3);
        map.setMoveable(reward2Pos,3);
        map.setMoveable(reward3Pos,3);
        map.setMoveable(reward4Pos,3);

        // 放入世界
        world.put(player, playerPos);
        world.put(shooter1, shooter1Pos);
        world.put(shooter2, shooter2Pos);
        world.put(shooter3, shooter3Pos);
        world.put(shooter4, shooter4Pos);
        world.put(shooter5, shooter5Pos);

        world.put(sworksMan1, sworksMan1Pos);
        world.put(sworksMan2, sworksMan2Pos);
        world.put(sworksMan3, sworksMan3Pos);
        world.put(sworksMan4, sworksMan4Pos);
        world.put(sworksMan5, sworksMan5Pos);
        world.put(sworksMan6, sworksMan6Pos);
        world.put(sworksMan7, sworksMan7Pos);
        world.put(sworksMan8, sworksMan8Pos);

        world.put(bonus1, reward1Pos);
        world.put(bonus2, reward2Pos);
        world.put(bonus3, reward3Pos);
        world.put(bonus4, reward4Pos);


        // 设置并启动线程
        exec = Executors.newCachedThreadPool();
        exec.execute(new Thread(player));
        exec.execute(new Thread(cannonballList));
        exec.execute(new Thread(shooter1));
        exec.execute(new Thread(shooter2));
        exec.execute(new Thread(shooter3));
        exec.execute(new Thread(shooter4));
        exec.execute(new Thread(shooter5));

        exec.execute(new Thread(sworksMan1));
        exec.execute(new Thread(sworksMan2));
        exec.execute(new Thread(sworksMan3));
        exec.execute(new Thread(sworksMan4));
        exec.execute(new Thread(sworksMan5));
        exec.execute(new Thread(sworksMan6));
        exec.execute(new Thread(sworksMan7));
        exec.execute(new Thread(sworksMan8));
    }

    @Override
    public void gameOverScreen() {
        world.setGameOverWorld();
    }

    // @Override
    // public void updateScreenState() {
    //     if (this.player != null) {
    //         if (world.getWorldState()  2) {
    //             world.setWorldState(3);
    //         }
    //     }
    // }

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
    public Screen releaseKey() {
        if(world.getWorldState() == 1){
            player.resetDirection();
        }
        return this;
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (world.getWorldState() == 0) { // 开始界面
            if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                world.setWorldState(1);
                startScreen();
            }
        } else if(world.getWorldState() == 1){
            switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.movePlayer(2);
                break;
            case KeyEvent.VK_DOWN:
                player.movePlayer(1);
                break;
            case KeyEvent.VK_LEFT:
                player.movePlayer(3);
                break;
            case KeyEvent.VK_RIGHT:
                player.movePlayer(4);
                break;
            }
        }
        return this;
    }
}
