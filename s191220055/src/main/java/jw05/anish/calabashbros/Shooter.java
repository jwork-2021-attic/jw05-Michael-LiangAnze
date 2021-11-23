package jw05.anish.calabashbros;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import jw05.anish.algorithm.HandleDist;
import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;
public class Shooter extends Creature implements Runnable  {
    private int rank;
    Player target;
    int detectnDistance;
    private int cd=1;//冷却时间
    Random random;
    CannonballList cannonballList;

    public Shooter(Color color, int rank, int speed,int hp, int detectnDistance, World world, Map map, Player enemy,CannonballList cannonballList) {
        super(color, (char) 2, world);
        this.rank = rank;
        this.speed = speed;
        this.hp = hp;
        this.detectnDistance = detectnDistance;
        this.map = map;
        target = enemy;
        this.cannonballList = cannonballList;
        random = new Random();
        this.cd = 3;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    public void setTarget(Player c) {
        this.target = c;
    }

    private int isFire(Tuple<Integer, Integer>curPos,Tuple<Integer, Integer> targetPos){
        // case1: direct fire
        if(curPos.first == targetPos.first){
            // 计算方向
            return curPos.second < targetPos.second?1:2;

        }
        else if(curPos.second == targetPos.second){
            return curPos.first < targetPos.first?4:3;
        }
        return 0;
    }


    private void fire(Tuple<Integer, Integer>curPos,int direction){
        if(direction == 1){
            cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first,curPos.second+1), direction);
        }
        else if(direction == 2){
            cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first,curPos.second-1), direction);
        }
        else if(direction == 3){
            cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first-1,curPos.second), direction);
        }
        else if(direction == 4){
            cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first+1,curPos.second), direction);
        }
    }

    private boolean randomWalk() {
        int d = random.nextInt(4) + 1;
        return moveTo(d);
    }

    @Override
    public void run() { // 该生物的移动、攻击都由run发起
        HandleDist hd = new HandleDist(map);
        int targetStepDirection = 0; // 下一步的走向
        Tuple<Integer, Integer> curPos = null, targetPos = null;

        while (true) {
            curPos = this.getPos();
            targetPos=target.getPos();
            if (target != null) {
                if ((targetStepDirection = isFire(curPos,targetPos)) != 0) { //处于攻击位置
                    if(cd == 5){
                        fire(curPos,targetStepDirection);
                        cd = 4;
                    }
                } else {// do nothing
                    randomWalk();
                }
            } else {
            
            }
            //处理cd
            if(cd != 5 && cd != 0){
                cd--;
            }
            else if(cd == 0){
                cd = 5;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {

            }
        }
    }
}
