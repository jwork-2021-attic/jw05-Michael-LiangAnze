package jw05.anish.calabashbros;

import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;
import java.awt.Color;

public class Creature extends Thing {

    int hp;
    int speed;
    Map map;

    Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
        speed = 100;
    }

    public boolean moveTo(int beginX, int beginY,int targetX,int targetY) {
        if(map.moveThing(beginX, beginY,targetX,targetY,false)){ //检查当前状态是否可以前往，如果可以就移动
            this.world.swapPos(new Tuple<>(beginX,beginY), new Tuple<>(targetX,targetY));
            return true;
        }
        else{
            System.out.println("blocked!");
            return false;
        }
        
    }

    public boolean moveTo(int direction) { // 1 2 3 4分别代表上下左右
        Tuple<Integer,Integer> curPos = getPos();
        int curX=curPos.first,curY=curPos.second;
        if (direction == 1) {
            return moveTo(curX,curY,curX, curY + 1);
        } else if (direction == 2) {
            return moveTo(curX,curY,curX, curY - 1);
        } else if (direction == 3) {
            return moveTo(curX,curY,curX - 1, curY);
        } else if (direction == 4) {
            return moveTo(curX,curY,curX + 1, curY);
        }
        else{
            System.out.println("direction:"+direction+" is illegal!");
            return false;
        }
    }

    public synchronized int getHp() {
        return hp;
    }

    public void disappear() {
        Tuple<Integer,Integer> curPos = getPos();
        this.world.put(new Floor(this.world), curPos.first, curPos.second);
        map.setMoveable(curPos.first, curPos.second, 0);
    }

    public synchronized void beAttack(int damage) {
        this.hp -= damage;
        if(this.hp <= 0){
            disappear();
        }
    }

    public synchronized void attack(Creature c, int damage) {
        c.beAttack(damage);
    }
}
