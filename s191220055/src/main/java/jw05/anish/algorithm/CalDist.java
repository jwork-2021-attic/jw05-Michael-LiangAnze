package jw05.anish.algorithm;
import java.util.LinkedList;
import java.util.Queue;

public class CalDist {
    int mapSize;
    int[][] dist = null;
    int[][] map;

    CalDist(int mapSize, int[][] map) {
        this.mapSize = mapSize;
        this.map = map;
        dist = new int[mapSize][mapSize];
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                dist[i][j] = -1;
            }
        }
    }

    int[][] getDist() {
        return dist;
    }

    void output() {
        for (int i = 0; i < 10; ++i) {
            for (int temp : dist[i]) {
                System.out.print(temp + " ");
            }
            System.out.print('\n');
        }
    }

    void calculateDist(int beginX, int beginY) {
        dist[beginX][beginY] = 0;
        Queue<Tuple<Integer, Integer>> Q = new LinkedList<>();
        Q.offer(new Tuple<Integer, Integer>(beginX, beginY));
        Tuple<Integer, Integer> temp;
        int tempx, tempy;
        while ((temp = Q.poll()) != null) {// queue is not empty
            // 检查上下左右是否被访问过
            tempx = temp.first;
            tempy = temp.second;
            if (map[tempx - 1][tempy] == 0 && dist[tempx - 1][tempy] == -1) {
                dist[tempx - 1][tempy] = dist[tempx][tempy] + 1;
                Q.offer(new Tuple<Integer, Integer>(tempx - 1, tempy));
            }
            if (map[tempx + 1][tempy] == 0 && dist[tempx + 1][tempy] == -1) {
                dist[tempx + 1][tempy] = dist[tempx][tempy] + 1;
                Q.offer(new Tuple<Integer, Integer>(tempx + 1, tempy));
            }
            if (map[tempx][tempy - 1] == 0 && dist[tempx][tempy - 1] == -1) {
                dist[tempx][tempy - 1] = dist[tempx][tempy] + 1;
                Q.offer(new Tuple<Integer, Integer>(tempx, tempy - 1));
            }
            if (map[tempx][tempy + 1] == 0 && dist[tempx][tempy + 1] == -1) {
                dist[tempx][tempy + 1] = dist[tempx][tempy] + 1;
                Q.offer(new Tuple<Integer, Integer>(tempx, tempy + 1));
            }
        }
    }

    
}