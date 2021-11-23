package jw05.anish.calabashbros;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import jw05.anish.algorithm.HandleDist;
import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;

public class Bomber extends Creature implements Runnable {
    private int rank;
    Player target;
    int detectnDistance;
    int sleepTime;
    Random random;

    public Bomber(Color color, int rank, int speed, int detectnDistance, int hp,World world, Map map, Player enemy) {
        super(color, (char) 2, world);
        this.rank = rank;
        this.speed = speed;
        this.detectnDistance = detectnDistance;
        this.map = map;
        this.hp = hp;
        target = enemy;
        random = new Random();
        this.sleepTime = 1000 / speed * 50;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    private boolean reachTarget(int beginX, int beginY, int targetX, int targetY) {
        if (beginX >= targetX - 1 && beginX <= targetX + 1 && beginY <= targetY + 1 && beginY >= targetY - 1) {
            return true;
        } else
            return false;
    }

    public void setTarget(Player c) {
        this.target = c;
    }

    private boolean enemyComing() { // 用以寻找敌人
        Tuple<Integer, Integer> curPos = getPos();
        Tuple<Integer, Integer> targetPos = target.getPos();
        return Math.abs(targetPos.first - curPos.first) <= detectnDistance
                && Math.abs(targetPos.second - curPos.second) <= detectnDistance;
    }

    private boolean randomWalk() {
        int d = random.nextInt(4) + 1;
        // System.out.println(d);
        return moveTo(d);
    }

    @Override
    public void run() { // 该生物的移动、攻击都由run发起
        HandleDist hd = new HandleDist(map);
        int nextStepDirection = 0; // 下一步的走向
        Tuple<Integer, Integer> curPos = null, targetPos = null;

        while (true) {
            if (target != null) {
                if (enemyComing()) { // enemy coming, go attack
                    this.changeColor(255, 0, 0);
                    curPos = getPos();
                    targetPos = target.getPos();
                    if (!reachTarget(curPos.first, curPos.second, targetPos.first, targetPos.second)) {
                        // 使用的是同一刻的地图，在calculateDist方法中获取
                        hd.calculateDist(curPos.first, curPos.second); // step1:从当前坐标开始计算到其余位置的最短距离
                        nextStepDirection = hd.getNextStep(targetPos.first, targetPos.second);// step2: 1 2 3 4 代表上下左右
                        moveTo(nextStepDirection);
                    } else {
                        if (target.getHp() >= 0) {
                            this.attack(target, 20);
                        } else {
                            target = null; // 目标消灭
                        }
                    }
                } else {// do nothing
                    this.changeColor(120, 120, 120);
                    while (!randomWalk())
                        ;
                }
            } else {
                this.changeColor(120, 120, 120);
                while (!randomWalk())
                    ;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {

            }
        }
    }
}
