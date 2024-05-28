package Levels;

public class Level {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create the level
    private int[][] levelData;


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Level(int[][] levelData){
        this.levelData = levelData;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Sprite index
    public int getSpriteIndex(int x, int y){
        return levelData[y][x];
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters

    public int[][] getLevelData() {
        return levelData;
    }

    public void setLevelData(int[][] levelData) {
        this.levelData = levelData;
    }


}
