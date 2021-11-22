package jw05.anish.calabashbros;

import java.awt.Color;
import jw05.anish.map.Map;

public class Calabash extends Creature implements Runnable {

    private int rank;

    public Calabash(Color color, int rank,int speed,  World world, Map map) {
        super(color, (char) 2, world);
        this.rank = rank;
        this.speed = speed;
        this.map = map;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }



    @Override
    public void run() { //该生物的移动、攻击都由run发起
        // int i = 0;
        // while (true) {
        //     if(i < 27){
        //         moveTo(getX(), getY() + 1);
        //         i++;
        //     }
        //     else{
        //         disappear();
        //     }
        //     try {
        //         TimeUnit.MILLISECONDS.sleep(200);
        //     } catch (InterruptedException e) {

        //     }
        // }
    }
}
