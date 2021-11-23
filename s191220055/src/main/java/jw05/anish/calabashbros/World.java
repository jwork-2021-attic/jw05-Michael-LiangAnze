package jw05.anish.calabashbros;

import java.awt.Color;
import jw05.anish.screen.ScreenInfo;
import jw05.anish.algorithm.Tuple;

public class World {

    public static final int WIDTH = 30;
    public static final int HEIGHT = WIDTH + 1;
    public final int hpStartPos = 3;
    public final int scoreStartPos = hpStartPos+15;
    public final int rulesStartX = 0;
    public final int rulesStartY = 0;
    public final int pressEnterX = 2;
    public final int pressEnterY = 10;
    public final int gameOverInfoX = 10;
    public final int gameOverInfoY = 13;
    private Tile<Thing>[][] tiles;
    private int worldState;

    public void setWorldState(int s){
        worldState = s;
    }

    public int getWorldState(){
        return worldState;
    }

    @SuppressWarnings(value = "all")
    public World() {
        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
            }
        }
    }

    public void setRulesWorld(){ //开始界面
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++){
                tiles[i][j].setThing(new ScreenInfo(this, new Color(0, 0, 0), 0));
            }
        }
        String []rules = {"Rules:",
                            " -Escape the maze and win.",
                            " -One heart damage per attack.",
                            " -Pick red hearts and add hp."};
        int lineNum = 0;
        for(String line :rules){
            for(int i = 0;i<line.length();++i){
                tiles[rulesStartX+i][rulesStartY+lineNum].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) line.charAt(i)));
            }
            lineNum+=2;
        }

        String temp = "PRESS ENTER TO START";
        for(int i = 0;i<temp.length();++i){
            tiles[pressEnterX+i][pressEnterY].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) temp.charAt(i)));
        }
    }

    public void setGameWorld(){ //游戏正式开始
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j].setThing(new Floor(this));
            }
        }
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = WIDTH; j < HEIGHT; ++j) {
                tiles[i][j].setThing(new ScreenInfo(this, new Color(0, 0, 0), 0));
            }
        }
    }

    public void setGameOverWorld(){
        String info = "GAME OVER";
        for(int i = 0;i<info.length();++i){
            tiles[gameOverInfoX+i][gameOverInfoY].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) info.charAt(i)));
        }
    }


    private int transformX(int xPos) {
        return xPos;
    }

    private int transformY(int YPos) {
        return YPos + WIDTH;
    }

    public void setInfo(int hp,int score){
        // 画出ui
        tiles[transformX(hpStartPos)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'H'));
        tiles[transformX(hpStartPos+1)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'P'));
        tiles[transformX(hpStartPos+2)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) ':'));
        tiles[transformX(scoreStartPos)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'S'));
        tiles[transformX(scoreStartPos+1)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'C'));
        tiles[transformX(scoreStartPos+2)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'O'));
        tiles[transformX(scoreStartPos+3)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'R'));
        tiles[transformX(scoreStartPos+4)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) 'E'));
        tiles[transformX(scoreStartPos+5)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 255, 255), (int) ':'));
        updateInfo(hp, score);
    }

    private void updateInfo(int hp,int score){
        for(int i = hpStartPos+3;i < scoreStartPos;++i){
            tiles[transformX(i)][transformY(0)].setThing(new ScreenInfo(this, new Color(0, 0, 0), 0));
        }
        for(int i = scoreStartPos+6;i < WIDTH;++i){
            tiles[transformX(i)][transformY(0)].setThing(new ScreenInfo(this, new Color(0, 0, 0), 0));
        }
        for(int i = 0;i < hp && i < scoreStartPos;++i){
            tiles[transformX(hpStartPos+3+i)][transformY(0)].setThing(new ScreenInfo(this, new Color(255, 0, 0), 3));
        }
        if(score > 0){
            int num1 = score % 10;
            int num2 = score / 10;
            if(num1 != 0){
                tiles[transformX(scoreStartPos+7)][transformY(0)].setThing(new ScreenInfo(this, new Color(30, 30, 240), (int) '0'+num1));
            }
            if(num2 != 0){
                tiles[transformX(scoreStartPos+6)][transformY(0)].setThing(new ScreenInfo(this, new Color(30, 30, 240), (int) '0'+num2));
            }
        }
        else if(score == 0){
            tiles[transformX(scoreStartPos+7)][transformY(0)].setThing(new ScreenInfo(this, new Color(30, 30, 240), (int) '0'));
        }
        else{
            tiles[transformX(scoreStartPos+6)][transformY(0)].setThing(new ScreenInfo(this, new Color(30, 30, 240), (int) '-'));
            tiles[transformX(scoreStartPos+7)][transformY(0)].setThing(new ScreenInfo(this, new Color(30, 30, 240), (int) '0'-score));
        }
    }
    public int getWorldSize() {
        return Math.min(WIDTH,HEIGHT);
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public void swapPos(Tuple<Integer, Integer> p1, Tuple<Integer, Integer> p2) {
        Thing t1 = get(p1.first, p1.second);
        Thing t2 = get(p2.first, p2.second);
        put(t2, p1.first, p1.second);
        put(t1, p2.first, p2.second);
    }
}
