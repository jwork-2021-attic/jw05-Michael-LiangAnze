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

    public void moveTo(int direction){ // 1 2 3 4分别代表上下左右
        if(direction == 1){
            // moveTo(getX(),getY()-1);
            moveTo(getX()-1,getY());
        }
        else if(direction == 2){
            // moveTo(getX(),getY()+1);
            moveTo(getX()+1,getY());
        }
        else if(direction == 3){
            // moveTo(getX()-1,getY());
            moveTo(getX(),getY()-1);
        }
        else if(direction == 4){
            moveTo(getX(),getY()+1);
        }
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
