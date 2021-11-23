package jw05.anish.calabashbros;
import java.awt.Color;

import jw05.anish.algorithm.Tuple;

public class Cannonball extends Thing{

    public Cannonball(World world) {
        super(Color.red, (char) 250, world);
    }

    public void destroy(){
        Tuple<Integer,Integer> pos = getPos();
        world.put(new Floor(world),pos.first,pos.second);
    }
}
