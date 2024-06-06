package Levels;

import Entities.Crabby;
import Game.Game;
import Objects.*;

import java.util.ArrayList;

public class Level {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create the level
    private int[][] levelData;
    // offset
    private final int levelTilesWide = 130;
    private int maxTileOffset;
    private int maxLevelOffsetX;
    //ArrayList
    private ArrayList<Crabby> crabs = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Container> containers = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Level(int[][] levelData){
        this.levelData = levelData;
        createEnemy(levelData);
        createPotions(levelData);
        createObjects(levelData);
        createSpikes(levelData);
        createCannons(levelData);
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

    //Create potions
    public void createPotions(int[][] levelData){
        potions = Potion.loadPotion(levelData);
    }

    //Create objects
    public void createObjects(int[][] levelData){
        containers = Container.loadContainer(levelData);
    }

    //Create spikes
    public void createSpikes(int[][] levelData){
        spikes = Spike.loadSpikes(levelData);
    }

    //Create cannons
    public void createCannons(int[][] levelData){
        cannons = Cannon.loadCannon(levelData);
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
