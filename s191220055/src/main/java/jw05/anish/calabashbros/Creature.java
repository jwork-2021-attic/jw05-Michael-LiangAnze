package jw05.anish.calabashbros;
import java.awt.Color;

public class Creature extends Thing {

    int hp=100;

    Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(new Floor(this.world),getX(),getY());
        this.world.put(this, xPos, yPos);      
    }

    public void disappear(){
        this.world.put(new Floor(this.world),getX(),getY());
    }
    public synchronized void beAttack(Creature c,int damage){
        this.hp -= damage;
    }
    public synchronized void attack(Creature c,int damage){
        c.beAttack(this, damage);
    }
}
