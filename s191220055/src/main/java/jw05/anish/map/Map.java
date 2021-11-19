package jw05.anish.map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Map {
    int[][] map;
    int mapSize = 0;
    String mapFile;

    public Map(int mapSize, String mapFile) {
        this.mapFile = mapFile;
        this.mapSize = mapSize;
        this.map = new int[mapSize][mapSize];// 1为墙,0为可行
    }

    public void loadMap() throws IOException {
        int i = 0;
        Scanner s = null;
        String str = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(mapFile)));
            while (s.hasNextLine()) {
                str = s.nextLine();
                String[] line = str.split(" ");
                for (int j = 0; j < mapSize; ++j) {
                    map[i][j] = Integer.parseInt(line[j]);
                }
                i++;
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
    public void outputMap(){
        for (int i = 0; i < mapSize; ++i) {
            for (int temp : map[i]) {
                System.out.print(temp + " ");
            }
            System.out.print('\n');
        }
    }
    public int[][] getMap() {
        return this.map;
    }
}
