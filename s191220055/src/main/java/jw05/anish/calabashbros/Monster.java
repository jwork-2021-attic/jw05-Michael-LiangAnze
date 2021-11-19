package jw05.anish.calabashbros;

import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.util.ArrayList;
import jw05.anish.algorithm.Tuple;
import jw05.anish.algorithm.HandleDist;
import jw05.anish.map.Map;

public class Monster extends Creature implements Runnable {
    private int rank;
    Calabash target;
    Map map;
    ArrayList<Tuple<Calabash, Integer>> enemyCalabashList;

    public Monster(Color color, int rank, World world, Map map, ArrayList<Tuple<Calabash, Integer>> enemies) {
        super(color, (char) 2, world);
        this.rank = rank;
        this.map = map;
        enemyCalabashList = enemies;
    }

    public void searchEnemy() {
        for (Tuple<Calabash, Integer> temp : enemyCalabashList) {
            if (temp.second == -1) {
                target = temp.first;
            }
        }
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    public void swap(Calabash another) {
        int x = this.getX();
        int y = this.getY();
        this.moveTo(another.getX(), another.getY());
        another.moveTo(x, y);
    }

    @Override
    public void run() { // 该生物的移动、攻击都由run发起
        searchEnemy();
        HandleDist hd = new HandleDist(map.getMapSize(), map.getMap());
        int nextStepDirection = 0; // 下一步的走向
        while (true) {
            // 移动到敌人的位置
            if(getX() != target.getX() || getY() != target.getY()){
                hd.resetDist();// 先清空
                hd.calculateDist(this.getX(), this.getY()); // 从当前坐标开始计算到其余位置的最短距离
                hd.output();
                hd.output();
                System.out.println(nextStepDirection);
                nextStepDirection = hd.getNextStep(target.getX(), target.getY());// 1 2 3 4 代表上下左右
                moveTo(nextStepDirection);
            }
            
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }
}
