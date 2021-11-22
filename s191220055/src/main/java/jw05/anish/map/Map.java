package jw05.anish.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Map {
    int[][] map;
    int mapSize = 0;
    String mapFile;
    Lock lock = null;

    public Map(int mapSize, String mapFile) {
        this.mapFile = mapFile;
        this.mapSize = mapSize;
        this.map = new int[mapSize][mapSize];// 0为可行，1为不可行(墙体或者生物)
        lock = new ReentrantLock(); // 可重入锁，防止冲突
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
                    map[i][j] = Integer.parseInt(line[j]);
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

    public void setMoveable(int x, int y, int flag) {
        lock.lock();
        map[x][y] = flag;
        lock.unlock();
    }

    public boolean moveCreature(int beginX, int beginY,int targetX,int targetY) {
        boolean res = false;
        lock.lock();
        if (map[targetX][targetY] == 0) {//允许移动
            map[beginX][beginY]=0;
            map[targetX][targetY]=1;
            res = true;
        } else {
            res = false;
        }
        lock.unlock();
        return res;
    }
}
