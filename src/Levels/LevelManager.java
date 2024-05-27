package Levels;

import Game.Game;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import Game.Game;

import static Game.Game.TILE_SIZE_SCALE;

public class LevelManager {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create the variables
    private Game game;

    //Create the level image
    private BufferedImage[] levelImage = new BufferedImage[315];
    private Level levelOne;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSaveFile.getLeveData());
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw the level
    public void draw (Graphics g){
        //Create index
        int index = 0;
        for (int i = 0; i < Game.TILE_HEIGHT; i++) {
            for (int j = 0; j < Game.TILE_WIDTH; j++) {
                index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelImage[index], j * TILE_SIZE_SCALE, i * TILE_SIZE_SCALE,
                        TILE_SIZE_SCALE, TILE_SIZE_SCALE, null);
            }
        }

    }

    //Update the level
    public void update() {

    }

    //Import outside sprites
    public void importOutsideSprites() {
        //Import the background
        BufferedImage images = LoadSaveFile.GetSpriteAtlas(LoadSaveFile.TILE_ATLAS);
        //Define the index
        int index = 0;
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 21; j++){
                index = i * 21 + j;
                levelImage[index] = images.getSubimage(j * 24, i * 24, 24, 24);
            }
        }

    }
}
