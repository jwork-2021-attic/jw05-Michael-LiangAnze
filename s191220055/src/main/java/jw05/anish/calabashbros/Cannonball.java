package jw05.anish.calabashbros;
import java.awt.Color;

import jw05.anish.algorithm.Tuple;

public class Cannonball extends Thing{

    private int direction;
    private int damage;
    private Player player;

    public Cannonball(int direction,int damage,World world,Player player) {
        super(Color.red, (char) 249, world);
        this.direction = direction;
        this.damage = damage;
        this.player = player;
    }

    public void destroy(){
        Tuple<Integer,Integer> cannonballPos = getPos();
        Tuple<Integer,Integer> playerPos = player.getPos();
        if(direction == 1){
            if(playerPos.first == cannonballPos.first && playerPos.second == cannonballPos.second+1){
                if(player.getHp() > 0){
                    player.beAttack(damage);
                }
            }
        }
        else if(direction == 2){
            if(playerPos.first == cannonballPos.first && playerPos.second == cannonballPos.second-1){
                if(player.getHp() > 0){
                    player.beAttack(damage);
                }              
            }
        }
        else if(direction == 3){
            if(playerPos.second == cannonballPos.second && playerPos.first == cannonballPos.first-1){
                if(player.getHp() > 0){
                    player.beAttack(damage);
                }               
            }
        }
        else if(direction == 4){
            if(playerPos.second == cannonballPos.second && playerPos.first == cannonballPos.first+1){
                if(player.getHp() > 0){
                    player.beAttack(damage);
                }           
            }
        }
        world.put(new Floor(world),cannonballPos.first,cannonballPos.second);
    }
}
