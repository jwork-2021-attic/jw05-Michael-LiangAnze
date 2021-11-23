package jw05.anish.calabashbros;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import jw05.anish.algorithm.HandleDist;
import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;

public class SworksMan extends Creature implements Runnable {
    private int rank;
    Player target;
    int detectnDistance;
    int damage;
    int sleepTime;
    Random random;
    private final Color alertOnColor = new Color(220, 228, 58);
    private final Color alertOffColor = new Color(170, 177, 24);
    private int x1,y1,x2,y2;
    private int[][]areaMap;

    public SworksMan(int rank, int speed, int detectnDistance, int damage, int hp,World world, Map map, Player enemy,int x1, int y1,int x2,int y2) {
        super(new Color(170, 177, 24), (char) 2, world);
        this.rank = rank;
        this.speed = speed;
        this.detectnDistance = detectnDistance;
        this.map = map;
        this.hp = hp;
        target = enemy;
        random = new Random();
        this.damage = damage;
        this.sleepTime = 1000 / speed * 50;
        setArea(x1, y1, x2, y2);
    }

    public void setArea(int x1,int y1,int x2, int y2){
        if(x1 > x2 || y1 > y2){
            System.out.println("invalid area");
        }
        else{
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        int mapSize = map.getMapSize();
        areaMap = new int[mapSize][mapSize];
        map.getMapState(areaMap);
        for(int i = 0;i < mapSize;++i){
            for(int j = 0;j < mapSize;++j){
                if(i < x1 || i > x2 || j < y1 || j > y2){
                    areaMap[i][j] = 1; //不可见
                }
            }
        }
        for(int i = 0;i < mapSize;++i){
            for(int j = 0;j < mapSize;++j){
                System.out.print(areaMap[i][j]+" ");
            }
            System.out.print('\n');
        }
    }
    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    private boolean reachTarget(int beginX, int beginY, int targetX, int targetY) {
        // if (beginX >= targetX - 1 && beginX <= targetX + 1 && beginY <= targetY + 1 && beginY >= targetY - 1) {
        //   return true;
        //} else
        //    return false;
        if(beginX == targetX){
            if(beginY == targetY - 1 || beginY == targetY + 1){
                return true;
            }
            else {
                return false;
            }
        }
        else if(beginY == targetY){
            if(beginX == targetX - 1|| beginX == targetX +1){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
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
    public void setOnAlert() {
        changeColor(alertOnColor);
    }

    @Override
    public void setOffAlert() {
        changeColor(alertOffColor);
    }

    @Override
    public void run() { // 该生物的移动、攻击都由run发起
        HandleDist hd = new HandleDist(map);
        int nextStepDirection = 0; // 下一步的走向
        Tuple<Integer, Integer> curPos = null, targetPos = null;

        while (true) {
            if (target != null) {
                if (enemyComing()) { // enemy coming, go attack
                    setOnAlert(); //警惕
                    curPos = getPos();
                    targetPos = target.getPos();
                    if (!reachTarget(curPos.first, curPos.second, targetPos.first, targetPos.second)) {
                        // 使用的是同一刻的地图，在calculateDist方法中获取
                        hd.calculateDist(curPos.first, curPos.second); // step1:从当前坐标开始计算到其余位置的最短距离
                        nextStepDirection = hd.getNextStep(targetPos.first, targetPos.second);// step2: 1 2 3 4 代表上下左右
                        moveTo(nextStepDirection);
                    } else {
                        if (target.getHp() >= 0) {
                            target.beAttack(damage);
                        } else {
                            target = null; // 目标消灭
                        }
                    }
                } else {// do nothing
                    setOffAlert();//解除警惕
                    while (!randomWalk())
                        ;
                }
            } else {
                setOffAlert();
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