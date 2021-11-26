package jw05.anish.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jw05.anish.algorithm.Tuple;
import jw05.anish.calabashbros.Cannonball;
import jw05.anish.calabashbros.Creature;
import jw05.anish.calabashbros.Thing;
import jw05.anish.calabashbros.World;

public class Map {
    private int[][] map;
    private final int mapSize = 40;
    String mapFile;
    private Lock lock = null;
    private ArrayList<Creature> creatureList;
    World world;

    public Map(String mapFile, World world) {
        this.mapFile = mapFile;
        this.map = new int[mapSize][mapSize];// 0为可行，1为玩家、炮弹、或者敌人，其余为地图元素
        lock = new ReentrantLock(); // 可重入锁，防止冲突
        this.world = world;// 每次一修改地图的状态，马上对world修改，防止出现问题
    }

    public void setCreatureList(ArrayList<Creature> creatureList) {
        this.creatureList = creatureList;
    }

    public void loadMap() throws IOException {
        int i = 0;
        Scanner s = null;
        String str = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(mapFile)));
            while (s.hasNextLine()) {
                str = s.nextLine();
                String[] line = str.split(" ");
                for (int j = 0; j < mapSize; ++j) {
                    map[j][i] = Integer.parseInt(line[j]);
                }
                i++;
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public void outputMap() {
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                System.out.print(map[j][i] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public void getMapState(int[][] buffer) { // 旨在获取地图一瞬间的状态
        lock.lock();
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                buffer[i][j] = map[i][j];
            }
        }
        lock.unlock();
    }

    public int getMapSize() {
        return mapSize;
    }

    // 攻击产生的伤害应当发生在以下两个函数
    // 1.炮弹刚好产生在目标所在的地方，目标立即收到伤害
    // 2.炮弹刚好移动到目标的位置，目标立即受到伤害

    public synchronized boolean moveThing(int beginX, int beginY, int targetX, int targetY, Boolean isCannonball,Cannonball c) {
        boolean res = false;
        lock.lock();
        if (map[targetX][targetY] == 0) {// 允许移动
            int temp = map[beginX][beginY];
            map[beginX][beginY] = map[targetX][targetY];
            map[targetX][targetY] = temp;
            world.swapPos(new Tuple<Integer, Integer>(beginX, beginY), new Tuple<Integer, Integer>(targetX, targetY));
            res = true;
        } else { //位置上可能有生物
            if (isCannonball) { // 炮弹要销毁
                Tuple<Integer,Integer>tempPos;
                map[beginX][beginY] = 0;
                c.destroy(new Tuple<Integer, Integer>(beginX, beginY));
                int index = -1;
                for(int i = 0;i < creatureList.size();++i){
                    tempPos = creatureList.get(i).getPos();
                    if(targetX == tempPos.first && targetY == tempPos.second){
                        creatureList.get(i).beAttack(1);;
                        if(creatureList.get(i).getHp() <= 0){
                            index = i;
                        }
                        break;
                    }
                }
                if(index != -1){
                    creatureList.remove(index);
                }
                // System.out.println(creatureList.size());
            }
            res = false;
        }
        lock.unlock();
        return res;
    }

    public synchronized boolean setThing(Tuple<Integer, Integer> pos, int type, Thing t, boolean isForced,Boolean isCannonball) {
        boolean res = false;
        lock.lock();
        if (!isForced) { // 非强制，炮弹也是
            if (map[pos.first][pos.second] == 0) { // empty
                map[pos.first][pos.second] = type;
                world.put(t, pos);
                res = true;
            }
            else{ // 位置上可能有生物
                if(isCannonball){
                    Tuple<Integer,Integer>tempPos;
                    for(int i = 0;i < creatureList.size();++i){
                        tempPos = creatureList.get(i).getPos();
                        if(pos.first == tempPos.first && pos.second == tempPos.second){
                            creatureList.get(i).beAttack(1);
                            break;
                        }
                    }
                }
            }
        } else {
            map[pos.first][pos.second] = type;
            world.put(t, pos);
            res = true;
        }

        lock.unlock();
        return res;
    }
}
