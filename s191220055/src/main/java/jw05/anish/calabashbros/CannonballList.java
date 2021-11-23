package jw05.anish.calabashbros;
import java.awt.Color;
import java.util.ArrayList;
import jw05.anish.algorithm.Tuple;
import jw05.anish.map.Map;
import jw05.anish.calabashbros.Player;

public class CannonballList implements Runnable {
    int speed;
    ArrayList<Tuple<Cannonball,Integer>> cannonballList = null; //坐标，方向
    Map map;
    Player player;
    int[][]mapList;
    World world;

    public CannonballList(Player player,Map map,World world){
        // cannonballList = new ArrayList<Tuple<Tuple<Integer,Integer>,Integer>>();
        cannonballList = new ArrayList<Tuple<Cannonball,Integer>>();
        this.map=map;
        int mapsize=map.getMapSize();
        mapList=new int[mapsize][mapsize];
        this.player = player;
        this.world = world;
    }

    public void addCannonball(Tuple<Integer,Integer>pos,int direction){
        Tuple<Cannonball,Integer> temp = new Tuple<Cannonball,Integer>();
        temp.first= new Cannonball(world);
        temp.second = direction;
        if(map.setThing(pos.first, pos.second, 1)){
            world.put(temp.first, pos.first, pos.second);
            cannonballList.add(temp);
        }
    }

    private void move(){
        // map.getMapState(mapList);//获取地图状态
        Tuple<Integer,Integer> pos = null;
        ArrayList<Tuple<Cannonball,Integer>>removeList = new ArrayList<Tuple<Cannonball,Integer>>();
        for(Tuple<Cannonball,Integer> c:cannonballList){
            pos = c.first.getPos();
            if(c.second == 1){
                if(map.moveThing(pos.first, pos.second, pos.first, pos.second+1,true)){
                    world.swapPos(pos, new Tuple<Integer,Integer>(pos.first,pos.second+1));
                }
                else{
                    removeList.add(c);
                    c.first.destroy();
                }   
            }
            else if(c.second == 2){
                if(map.moveThing(pos.first, pos.second, pos.first, pos.second-1,true)){
                    world.swapPos(pos, new Tuple<Integer,Integer>(pos.first,pos.second-1));
                }
                else {
                    removeList.add(c);
                    c.first.destroy();
                }
            }
            else if(c.second == 3){
                if(map.moveThing(pos.first, pos.second, pos.first-1, pos.second,true)){
                    world.swapPos(pos, new Tuple<Integer,Integer>(pos.first-1,pos.second));
                }
                else{
                    removeList.add(c);
                    c.first.destroy();
                }
            }
            else if(c.second == 4){
                if(map.moveThing(pos.first, pos.second, pos.first+1, pos.second,true)){
                    world.swapPos(pos,new Tuple<Integer,Integer>(pos.first+1,pos.second));
                }
                else{
                    removeList.add(c);
                    c.first.destroy();
                }
            }
            
        }
        for(Tuple<Cannonball,Integer> i:removeList){
            cannonballList.remove(i);
        }
        System.out.println("cur size:"+cannonballList.size());
    }

    @Override
    public void run(){
        while(true){
            if(cannonballList.size() != 0){
                move();
            }
            try{
                Thread.sleep(400);
            }
            catch(InterruptedException e){
                
            }
        }  
    }
}