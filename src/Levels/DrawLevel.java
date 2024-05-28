package Levels;

import Game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilization.LoadSaveFile.importMap;

public class DrawLevel {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create Game
    private Game game;
    //Create the images
    private BufferedImage background1;
    private BufferedImage background2;
    private BufferedImage background3;
    private BufferedImage level1Image;
    //Create the paths
    private final String BACKGROUND1 = "/Texture/Entities/OakWoods/background/background_layer_1.png";
    private final String BACKGROUND2 = "/Texture/Entities/OakWoods/background/background_layer_2.png";
    private final String BACKGROUND3 = "/Texture/Entities/OakWoods/background/background_layer_3.png";
    private final String LEVEL1IMAGE   = "/TextureCreated/LevelOne.png";
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public DrawLevel(Game game){
        this.game = game;
        background1 = importMap(BACKGROUND1);
        background2 = importMap(BACKGROUND2);
        background3 = importMap(BACKGROUND3);
        level1Image = importMap(LEVEL1IMAGE);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw the level
    public void draw (Graphics g){
        g.drawImage(background1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(background2, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(background3, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(level1Image, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
    }


}
