package jw05.anish.calabashbros;

import java.awt.Color;

import javax.swing.text.html.HTMLDocument.BlockElement;

import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;

public class Player extends Creature implements Runnable {

    private int rank;
    private int direction;
    private int sleepTime;
    private int score;
    private int mapSize;
    int[][]mapList;

    public Player(Color color, int rank, int speed, int hp, World world, Map map) {
        super(color, (char) 14, world);
        this.rank = rank;
        this.speed = speed;
        this.map = map;
        this.hp = hp;
        this.direction = 0;
        score = 0;
        this.sleepTime = 1000 / speed * 50;
        world.setInfo(hp, score);
        mapSize = map.getMapSize();
        this.mapList = new int[mapSize][mapSize];
    }

    public void movePlayer(int direction) {
        this.direction = direction;
    }

    public void resetDirection() {
        this.direction = 0;
    }

    public int getRank() {
        return this.rank;
    }

    public synchronized void beAttack(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) { // 死亡
            disappear();
            world.setWorldState(3);
        }
        // score--;
        world.setInfo(hp, score);
    }

    public void getBonus(){
        this.hp++;
        this.score++;
        world.setInfo(hp, score);
    }

    public boolean isReward(Tuple<Integer,Integer> pos,int direction){
        boolean res = false;
        Tuple<Integer,Integer> tempPos;
        switch(direction){
            case 1:
                if(mapList[pos.first][pos.second+1] == 99){
                    res = true;
                    tempPos = new Tuple<Integer,Integer>(pos.first,pos.second+1);
                    world.put(new Floor(world), tempPos);
                    map.setMoveable(tempPos, 0);
                }
                else {
                    res = false;
                }  break;
            case 2:
                if(mapList[pos.first][pos.second-1] == 99){
                    res = true;
                    tempPos = new Tuple<Integer,Integer>(pos.first,pos.second-1);
                    world.put(new Floor(world), tempPos);
                    map.setMoveable(tempPos, 0);
                }
                else {
                    res = false;
                }  break;
            case 3:
                if(mapList[pos.first-1][pos.second] == 99){
                    res = true;
                    tempPos = new Tuple<Integer,Integer>(pos.first-1,pos.second);
                    world.put(new Floor(world), tempPos);
                    map.setMoveable(tempPos, 0);
                }
                else {
                    res = false;
                }  break;
            case 4:
                if(mapList[pos.first+1][pos.second] == 99){
                    res = true;
                    tempPos = new Tuple<Integer,Integer>(pos.first+1,pos.second);
                    world.put(new Floor(world), tempPos);
                    map.setMoveable(tempPos, 0);
                }
                else {
                    res = false;
                }  break;
        }
        return res;
    }

    private boolean isWin(Tuple<Integer,Integer> pos){
        return pos.first == mapSize-2 && pos.second == mapSize-2;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    @Override
    public void run() {
        Tuple<Integer,Integer>pos = null;
        map.getMapState(mapList); //注意位置，此时Player还未运行，但是奖励已经设置
        while (world.getWorldState() < 2) {
            pos = this.getPos();
            if(isWin(pos)){
                world.setWorldState(2);
                break;
            }
            try {
                if (direction != 0) {
                    if(!moveTo(direction)){
                        if(isReward(pos,direction)){
                            getBonus();
                        }
                    }

                }
                Thread.sleep(sleepTime); // state:sleeping
            } catch (InterruptedException e) {

            }
        }
    }
}
