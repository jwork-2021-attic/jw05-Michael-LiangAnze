package jw05.anish.calabashbros;

import java.awt.Color;

import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;

public class Player extends Creature implements Runnable {

    private int rank;
    private int direction;
    private int lastDirection; // 用于开火
    private int sleepTime;
    private int score;
    private int mapSize;
    private CannonballList cannonballList;
    private boolean attackState;
    private int cd = 5;
    int[][] mapList;

    public Player(Color color, int rank, int speed, int hp, World world, Map map, CannonballList cannonballList) {
        super(color, (char) 14, world);
        this.rank = rank;
        this.speed = speed;
        this.map = map;
        this.hp = hp;
        this.cannonballList = cannonballList;
        this.direction = 0;
        this.lastDirection = 4;
        score = 0;
        this.sleepTime = 1000 / speed * 50;
        world.setPlayerInfo(hp, score);
        mapSize = map.getMapSize();
        this.mapList = new int[mapSize][mapSize];
        attackState = false;
    }

    public void setAttackState() {
        this.attackState = true;
    }

    public void attack(Tuple<Integer, Integer> curPos) {
        switch (lastDirection) {
            case 1:
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first, curPos.second + 1),
                        lastDirection);
                break;
            case 2:
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first, curPos.second - 1),
                        lastDirection);
                break;
            case 3:
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first - 1, curPos.second),
                        lastDirection);
                break;
            case 4:
                cannonballList.addCannonball(new Tuple<Integer, Integer>(curPos.first + 1, curPos.second),
                        lastDirection);
                break;
        }
    }

    public void movePlayer(int direction) {
        this.lastDirection = direction;
        this.direction = direction;
    }

    public void resetDirection() {
        this.direction = 0;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public synchronized void beAttack(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) { // 死亡
            world.setWorldState(3);
        }
        world.setPlayerInfo(hp, score);
    }

    public void tryToGetReward(Tuple<Integer, Integer> pos, int direction) {
        boolean isReward = false;
        switch (direction) {
            case 1:
                if (mapList[pos.first][pos.second + 1] == 99) {
                    isReward = true;
                    map.setThing(new Tuple<Integer, Integer>(pos.first, pos.second + 1), 0, new Floor(world), true,
                            false);
                } else {
                    isReward = false;
                }
                break;
            case 2:
                if (mapList[pos.first][pos.second - 1] == 99) {
                    isReward = true;
                    map.setThing(new Tuple<Integer, Integer>(pos.first, pos.second - 1), 0, new Floor(world), true,
                            false);
                } else {
                    isReward = false;
                }
                break;
            case 3:
                if (mapList[pos.first - 1][pos.second] == 99) {
                    isReward = true;
                    map.setThing(new Tuple<Integer, Integer>(pos.first - 1, pos.second), 0, new Floor(world), true,
                            false);
                } else {
                    isReward = false;
                }
                break;
            case 4:
                if (mapList[pos.first + 1][pos.second] == 99) {
                    isReward = true;
                    map.setThing(new Tuple<Integer, Integer>(pos.first + 1, pos.second), 0, new Floor(world), true,
                            false);
                } else {
                    isReward = false;
                }
                break;
        }
        if (isReward) {
            this.hp++;
            this.score++;
            world.setPlayerInfo(hp, score);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    @Override
    public void run() {
        Tuple<Integer, Integer> pos = null;
        map.getMapState(mapList); // 注意位置，此时Player还未运行，但是奖励已经设置
        while (world.getWorldState() < 2 && this.getHp() > 0) {
            pos = this.getPos();
            if (attackState) {
                if (cd == 5) { // 冷却时间结束
                    attack(pos);
                    cd = 4;
                }
                attackState = false;
            }
            if (direction != 0) {
                if (!moveTo(direction)) {
                    tryToGetReward(pos, direction);
                }
            }
            if (cd == 0) {
                cd = 5;
            } else if (cd < 5) {
                cd--;
            }
            try {
                Thread.sleep(sleepTime); // state:sleeping
            } catch (InterruptedException e) {

            }
        }
    }
}
