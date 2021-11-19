package jw05.anish.algorithm;
import java.util.LinkedList;
import java.util.Queue;

public class HandleDist {
    int mapSize;
    int[][] dist = null;
    int[][] map;

    public HandleDist(int mapSize, int[][] map) {
        this.mapSize = mapSize;
        this.map = map;
        dist = new int[mapSize][mapSize];
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                dist[i][j] = -1;
            }
        }
    }

    public int[][] getDist() {
        return dist;
    }

    public void output() {
        for (int i = 0; i < mapSize; ++i) {
            for (int temp : dist[i]) {
                System.out.print(temp + " ");
            }
            System.out.print('\n');
        }
    }

    public void calculateDist(int beginX, int beginY) {
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

    public void resetDist(){
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                dist[i][j] = -1;
            }
        }
    }

    public int getNextStep(int targetX,int targetY){ // 1 2 3 4 分别代表上下左右
        int distance = dist[targetX][targetY];
        int res = 0;
        System.out.println(distance);
        while(distance > 0){
            //选择dist为distance-1的一块砖进行移动
            distance = distance-1;
            if(dist[targetX-1][targetY] == distance){
                targetX=targetX-1;
                res = distance == 0?2:0;
            }
            else if(dist[targetX+1][targetY] == distance){
                targetX=targetX+1;
                res = distance == 0?1:0;
            }
            else if(dist[targetX][targetY-1] == distance){
                targetY=targetY-1;
                res = distance == 0?4:0;
            }
            else if(dist[targetX][targetY+1] == distance){
                targetY=targetY+1;
                res = distance == 0?3:0;
            }
        }
        return res;
    }
    
}