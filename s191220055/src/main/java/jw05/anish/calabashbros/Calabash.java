package jw05.anish.calabashbros;

import java.util.concurrent.TimeUnit;
import java.awt.Color;

public class Calabash extends Creature implements Runnable {

    private int rank;

    public Calabash(Color color, int rank, World world) {
        super(color, (char) 2, world);
        this.rank = rank;
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
    public void run() { //该生物的移动、攻击都由run发起
        int i = 0;
        while (true) {
            if(i < 27){
                moveTo(getX(), getY() + 1);
                i++;
            }
            else{
                disappear();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }
}
