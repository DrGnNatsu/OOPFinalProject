package Levels;

import Entities.Crabby;
import Game.Game;

import java.util.ArrayList;

public class Level {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create the level
    private int[][] levelData;
    // offset
    private final int levelTilesWide = 130;
    private int maxTileOffset;
    private int maxLevelOffsetX;
    //
    private ArrayList<Crabby> crabs = new ArrayList<>();

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Level(int[][] levelData){
        this.levelData = levelData;
        createEnemy(levelData);
        calculateOffset();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Calculate offset
    private void calculateOffset(){
        maxTileOffset = levelTilesWide  - Game.TILE_WIDTH;
        maxLevelOffsetX = Game.TILE_SIZE_SCALE * maxTileOffset;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create enemy
    public void createEnemy(int[][] levelData){
        crabs = Crabby.getCrabArmy(levelData);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters

    public int[][] getLevelData() {
        return levelData;
    }

    public void setLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }
}
