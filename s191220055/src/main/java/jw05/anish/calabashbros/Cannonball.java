package jw05.anish.calabashbros;

import java.awt.Color;

import jw05.anish.algorithm.Tuple;

public class Cannonball extends Thing {

    private int direction;

    public Cannonball(int direction, int damage, World world) {
        super(Color.red, (char) 249, world);
        this.direction = direction;
    }

    public int getDirection(){
        return this.direction;
    }

    public void destroy(Tuple<Integer, Integer>pos) {
        world.put(new Floor(world), pos);
    }
}
