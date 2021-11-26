package jw05.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jw05.anish.calabashbros.Player;
import jw05.anish.calabashbros.Shooter;
import jw05.anish.calabashbros.SworksMan;
import jw05.anish.algorithm.Tuple;
import jw05.anish.calabashbros.Reward;
import jw05.anish.calabashbros.CannonballList;
import jw05.anish.calabashbros.Creature;
import jw05.anish.calabashbros.MapItem;
import jw05.anish.calabashbros.World;
import jw05.asciiPanel.AsciiPanel;
import jw05.anish.map.Map;

public class WorldScreen implements Screen {

    private World world;
    private Map map;
    private Player player;
    private CannonballList cannonballList;
    private ArrayList<Creature>creatureList;
    private ExecutorService exec;

    public WorldScreen() {
        world = new World();
        world.setWorldState(0);
    }

    private void generateOriginalMap() {
        try {
            map = new Map("src/main/java/jw05/anish/map/testmap.txt",world);
            map.loadMap();
            int mapSize = map.getMapSize();
            int[][] tempMap = new int[mapSize][mapSize];
            map.getMapState(tempMap);
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    // 对于地图物件的规定：
                    // 必须在1~9之间
                    // 1为墙
                    // 2为水
                    // 3为树
                    switch(tempMap[i][j]){
                        case 1:world.put(new MapItem(AsciiPanel.cyan,177,this.world), new Tuple<Integer,Integer>(i,j));break;
                        case 2:world.put(new MapItem(new Color(30,144,255),126,this.world), new Tuple<Integer,Integer>(i,j));break;
                        case 3:world.put(new MapItem(AsciiPanel.green,24,this.world), new Tuple<Integer,Integer>(i,j));break;
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
    public void gamingScreen() {
        world.setGamingWorld();
        generateOriginalMap();
        creatureList = new ArrayList<Creature>(); 
        map.setCreatureList(creatureList);
        cannonballList = new CannonballList(1, 300, map, world);

        // 创建人物和道具
        player = new Player(new Color(0 , 245, 255), 1, 500, 4, world, map,cannonballList);
        
        // Shooter shooter1 = new Shooter(1, 100, 1, world, map, player, cannonballList, 3, 1, 14, 3);
        // Shooter shooter2 = new Shooter(1, 100, 1, world, map, player, cannonballList, 7, 37, 13, 38);
        // Shooter shooter3 = new Shooter(1, 100, 1, world, map, player, cannonballList, 13, 14, 16, 18);
        // Shooter shooter4 = new Shooter(1, 100, 1, world, map, player, cannonballList, 22, 3, 27, 5);
        // Shooter shooter5 = new Shooter(1, 100, 1, world, map, player, cannonballList, 26, 35, 38, 38);

        // SworksMan sworksMan1 = new SworksMan(1, 100, 4,1, 1, world, map,player,1,11,11,18);
        // SworksMan sworksMan2 = new SworksMan(1, 100, 4,1, 1, world, map,player,1,22,11,28);
        // SworksMan sworksMan3 = new SworksMan(1, 100, 4,1, 1, world, map,player,8,29,15,35);
        // SworksMan sworksMan4 = new SworksMan(1, 100, 4,1, 1, world, map,player,11,22,24,28);
        // SworksMan sworksMan5 = new SworksMan(1, 100, 5,1, 1, world, map,player,24,9,35,16);
        // SworksMan sworksMan6 = new SworksMan(1, 100, 4,1, 1, world, map,player,18,18,26,24);
        // SworksMan sworksMan7 = new SworksMan(1, 100, 4,1, 1, world, map,player,15,29,24,38);
        // SworksMan sworksMan8 = new SworksMan(1, 100, 4,1, 1, world, map,player,26,22,38,31);

        // Reward reward1 = new Reward(world);
        // Reward reward2 = new Reward(world);
        // Reward reward3 = new Reward(world);
        // Reward reward4 = new Reward(world);

        //将生物添加到队列中
        creatureList.add(player);

        // creatureList.add(shooter1);
        // creatureList.add(shooter2);
        // creatureList.add(shooter3);
        // creatureList.add(shooter4);
        // creatureList.add(shooter5);

        // creatureList.add(sworksMan1);
        // creatureList.add(sworksMan2);
        // creatureList.add(sworksMan3);
        // creatureList.add(sworksMan4);
        // creatureList.add(sworksMan5);
        // creatureList.add(sworksMan6);
        // creatureList.add(sworksMan7);
        // creatureList.add(sworksMan8);


        //创建位置
        Tuple<Integer,Integer> playerPos = new Tuple<Integer,Integer>(20,20);

        // Tuple<Integer,Integer> playerPos = new Tuple<Integer,Integer>(1,1);

        // Tuple<Integer,Integer> shooter1Pos = new Tuple<Integer,Integer>(9,3);
        // Tuple<Integer,Integer> shooter2Pos = new Tuple<Integer,Integer>(8,37);
        // Tuple<Integer,Integer> shooter3Pos = new Tuple<Integer,Integer>(15,16);
        // Tuple<Integer,Integer> shooter4Pos = new Tuple<Integer,Integer>(24,4);
        // Tuple<Integer,Integer> shooter5Pos = new Tuple<Integer,Integer>(31,37);

        // Tuple<Integer,Integer> sworksMan1Pos = new Tuple<Integer,Integer>(4,13);
        // Tuple<Integer,Integer> sworksMan2Pos = new Tuple<Integer,Integer>(6,25);
        // Tuple<Integer,Integer> sworksMan3Pos = new Tuple<Integer,Integer>(10,32);
        // Tuple<Integer,Integer> sworksMan4Pos = new Tuple<Integer,Integer>(15,25);
        // Tuple<Integer,Integer> sworksMan5Pos = new Tuple<Integer,Integer>(28,12);
        // Tuple<Integer,Integer> sworksMan6Pos = new Tuple<Integer,Integer>(22,21);
        // Tuple<Integer,Integer> sworksMan7Pos = new Tuple<Integer,Integer>(18,34);
        // Tuple<Integer,Integer> sworksMan8Pos = new Tuple<Integer,Integer>(32,28);

        // Tuple<Integer,Integer> reward1Pos = new Tuple<Integer,Integer>(13,12);
        // Tuple<Integer,Integer> reward2Pos = new Tuple<Integer,Integer>(9,1);
        // Tuple<Integer,Integer> reward3Pos = new Tuple<Integer,Integer>(38,8);
        // Tuple<Integer,Integer> reward4Pos = new Tuple<Integer,Integer>(15,35);


        // 设置地图及世界,是否需要和上一部分互换位置？
        map.setThing(playerPos, 1,player,false,false);

        // map.setThing(shooter1Pos, 1,shooter1,false,false);
        // map.setThing(shooter2Pos, 1,shooter2,false,false);
        // map.setThing(shooter3Pos, 1,shooter3,false,false);
        // map.setThing(shooter4Pos, 1,shooter4,false,false);
        // map.setThing(shooter5Pos, 1,shooter5,false,false);

        // map.setThing(sworksMan1Pos, 1,sworksMan1,false,false);
        // map.setThing(sworksMan2Pos, 1,sworksMan2,false,false);
        // map.setThing(sworksMan3Pos, 1,sworksMan3,false,false);
        // map.setThing(sworksMan4Pos, 1,sworksMan4,false,false);
        // map.setThing(sworksMan5Pos, 1,sworksMan5,false,false);
        // map.setThing(sworksMan6Pos, 1,sworksMan6,false,false);
        // map.setThing(sworksMan7Pos, 1,sworksMan7,false,false);
        // map.setThing(sworksMan8Pos, 1,sworksMan8,false,false);

        // map.setThing(reward1Pos, 99,reward1,false,false);
        // map.setThing(reward2Pos, 99,reward2,false,false);
        // map.setThing(reward3Pos, 99,reward3,false,false);
        // map.setThing(reward4Pos, 99,reward4,false,false);

        // 设置并启动线程
        exec = Executors.newCachedThreadPool();
        exec.execute(new Thread(player));
        exec.execute(new Thread(cannonballList));
        // exec.execute(new Thread(shooter1));
        // exec.execute(new Thread(shooter2));
        // exec.execute(new Thread(shooter3));
        // exec.execute(new Thread(shooter4));
        // exec.execute(new Thread(shooter5));

        // exec.execute(new Thread(sworksMan1));
        // exec.execute(new Thread(sworksMan2));
        // exec.execute(new Thread(sworksMan3));
        // exec.execute(new Thread(sworksMan4));
        // exec.execute(new Thread(sworksMan5));
        // exec.execute(new Thread(sworksMan6));
        // exec.execute(new Thread(sworksMan7));
        // exec.execute(new Thread(sworksMan8));
    }

    @Override
    public void gameOverScreen() {
        world.setGameOverWorld();
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
                gamingScreen();
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
            case KeyEvent.VK_SPACE:
                player.setAttackState();
            }
        }
        return this;
    }
}
