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
    private BufferedImage level2Image;
    //Create the paths
    private final String BACKGROUND1 = "/Texture/Entities/OakWoods/background/background_layer_1.png";
    private final String BACKGROUND2 = "/Texture/Entities/OakWoods/background/background_layer_2.png";
    private final String BACKGROUND3 = "/Texture/Entities/OakWoods/background/background_layer_3.png";
    private final String LEVEL1IMAGE = "/TextureCreated/1.png";
    private final String LEVEL2IMAGE = "/TextureCreated/2.png";
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public DrawLevel(Game game){
        this.game = game;
        background1 = importMap(BACKGROUND1);
        background2 = importMap(BACKGROUND2);
        background3 = importMap(BACKGROUND3);
        level1Image = importMap(LEVEL1IMAGE);
        level2Image = importMap(LEVEL2IMAGE);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw the level
    public void draw (Graphics g , int xLevelOffset){
        drawBackground(g, xLevelOffset);
        g.drawImage(level1Image, - xLevelOffset, 0, level1Image.getWidth() * 3 / 2, level1Image.getHeight() * 3 / 2, null);
        //g.drawImage(level2Image, - xLevelOffset, 0, level2Image.getWidth() * 3 / 2, level2Image.getHeight() * 3 / 2, null);
    }

    public void drawBackground(Graphics g, int xLevelOffset){
        g.drawImage(background1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(background2, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(background3, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
    }


}
