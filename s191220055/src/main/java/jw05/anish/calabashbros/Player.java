package jw05.anish.calabashbros;

import java.awt.Color;

import jw05.anish.map.Map;

public class Player extends Creature implements Runnable {

    private int rank;
    private int direction;
    private int sleepTime;
    private int score;
    private boolean isAlive;

    public Player(Color color, int rank, int speed, int hp, World world, Map map) {
        super(color, (char) 2, world);
        this.rank = rank;
        this.speed = speed;
        this.map = map;
        this.hp = hp;
        this.direction = 0;
        score = 1;
        this.sleepTime = 1000 / speed * 50;
        world.setInfo(hp, score);
        this.isAlive = true;

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
        if(this.hp <= 0){ //死亡
            disappear();
            world.setWorldState(2);
            isAlive=false;
        }
        score--;
        world.setInfo(hp,score);
    }

    public boolean isAlive(){
        return isAlive;
    }
    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    @Override
    public void run() {
        while (world.getWorldSize() != 2) {
            try {
                if (direction != 0) {
                    moveTo(direction);
                }
                Thread.sleep(sleepTime); //state:sleeping        
            } catch (InterruptedException e) {

            }
        }
    }
}
