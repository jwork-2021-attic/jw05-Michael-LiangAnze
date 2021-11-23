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
    private int cd;//冷却时间
    private int sleepTime;
    Random random;
    private final Color alertOnColor = new Color(255, 0, 0);
    private final Color alertOffColor = new Color(162, 45, 95);
    CannonballList cannonballList;
    int cannonDamage;

    public Shooter(int rank, int speed,int hp, int detectnDistance, World world, Map map, Player enemy,CannonballList cannonballList) {
        super(new Color(162, 45, 95), (char) 2, world);
        this.rank = rank;
        this.speed = speed;
        this.hp = hp;
        this.detectnDistance = detectnDistance;
        this.map = map;
        target = enemy;
        this.cannonballList = cannonballList;
        random = new Random();
        this.sleepTime = 1000 / speed * 50;
        this.cd = 1;
        cannonDamage = cannonballList.getDamage();
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
        if(curPos.first == targetPos.first){
            // 计算方向
            return curPos.second < targetPos.second?1:2;
        }
        else if(curPos.second == targetPos.second){
            return curPos.first < targetPos.first?4:3;
        }   

        return 0;
    }


    private void fire(Tuple<Integer, Integer>curPos,Tuple<Integer, Integer>targetPos,int direction){
        if(direction == 1){
            if(targetPos.first == curPos.first && targetPos.second == curPos.second+1){ //敌人正好在炮弹的位置
                target.beAttack(cannonDamage);
            }
            else{
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first,curPos.second+1), direction);
            }
        }
        else if(direction == 2){
            if(targetPos.first == curPos.first && targetPos.second == curPos.second-1){
                target.beAttack(cannonDamage);
            }
            else{
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first,curPos.second-1), direction);
            }
        }
        else if(direction == 3){
            if(targetPos.first == curPos.first-1 && targetPos.second == curPos.second){
                target.beAttack(cannonDamage);
            }
            else {
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first-1,curPos.second), direction);
            }      
        }
        else if(direction == 4){
            if(targetPos.first == curPos.first+1 && targetPos.second == curPos.second){
                target.beAttack(cannonDamage);
            }
            else{
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first+1,curPos.second), direction);
            }
        }
    }

    private boolean randomWalk() {
        int d = random.nextInt(4) + 1;
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
        int targetStepDirection = 0; // 下一步的走向
        Tuple<Integer, Integer> curPos = null, targetPos = null;

        while (true) {
            if (target != null) {
                curPos = this.getPos();
                targetPos=target.getPos();
                if ((targetStepDirection = isFire(curPos,targetPos)) != 0) { //处于攻击位置
                    setOnAlert(); // 警惕
                    if(cd == 5){    //冷却时间结束
                        if(target.getHp()>0){
                            fire(curPos,targetPos,targetStepDirection);
                            cd = 4;
                        }
                        else{   //目标消灭
                            target = null;
                            setOffAlert();
                        }
                    }
                } else {// do nothing
                    setOffAlert();
                    randomWalk();
                }
            } else {
                randomWalk();
            }
            //处理cd
            if(cd != 5 && cd != 0){
                cd--;
            }
            else if(cd == 0){
                cd = 5;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {

            }
        }
    }
}
