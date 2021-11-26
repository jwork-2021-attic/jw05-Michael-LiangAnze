package jw05.anish.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jw05.anish.algorithm.Tuple;
import jw05.anish.calabashbros.Cannonball;
import jw05.anish.calabashbros.Thing;
import jw05.anish.calabashbros.World;

public class Map {
    private int[][] map;
    private final int mapSize = 40;
    String mapFile;
    private Lock lock = null;
    World world;

    public Map(String mapFile,World world) {
        this.mapFile = mapFile;
        this.map = new int[mapSize][mapSize];// 0为可行，1为玩家、炮弹、或者敌人，其余为地图元素
        lock = new ReentrantLock(); // 可重入锁，防止冲突
        this.world = world;//每次一修改地图的状态，马上对world修改，防止出现问题
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
            for (int temp : map[i]) {
                System.out.print(temp + " ");
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

    public void setMoveable(Tuple<Integer,Integer>pos, int flag) {
        lock.lock();
        map[pos.first][pos.second] = flag;
        lock.unlock();
    }

    public synchronized boolean moveThing(int beginX, int beginY, int targetX, int targetY, Boolean isDestroyCannonball, Cannonball c) {// 最后两个参数给炮弹，如果移动失败且该参数不为空，则将炮弹销毁
        boolean res = false;
        lock.lock();
        if (map[targetX][targetY] == 0) {// 允许移动
            int temp = map[beginX][beginY];
            map[beginX][beginY] = map[targetX][targetY];
            map[targetX][targetY] = temp;
            world.swapPos(new Tuple<Integer,Integer>(beginX,beginY),new Tuple<Integer,Integer>(targetX,targetY));
            res = true;
        } else {
            if (isDestroyCannonball) { //炮弹要销毁
                map[beginX][beginY] = 0;
                c.destroy();
            }
            res = false;
        }
        lock.unlock();
        return res;
    }

    public boolean setThing(Tuple<Integer,Integer>pos, int type,Thing t) {
        boolean res = false;
        lock.lock();
        if (map[pos.first][pos.second] == 0) { // empty
            map[pos.first][pos.second] = type;
            world.put(t, pos);
            res = true;
        }
        lock.unlock();
        return res;
    }
}
