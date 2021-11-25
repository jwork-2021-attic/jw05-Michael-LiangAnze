package jw05.anish.calabashbros;

import java.awt.Color;

public class Floor extends Thing {

    public Floor(World world, Color c) {
        super(c, (char) 250, world);
    }

    public Floor(World world) {
        super(Color.gray, (char) 250, world);
    }
}
