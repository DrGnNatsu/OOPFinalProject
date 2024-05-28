package Utilization;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static Game.Game.*;

public class LoadSaveFile {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Path for the player sprite
    public static final String PLAYER_ATLAS1 = "/Texture/Entities/GenericChar/png/blue/CharBlue1.png";
    public static final String PLAYER_ATLAS2 = "/Texture/Entities/GenericChar/png/blue/CharBlue2.png";
    //Path for background
    //public static final String LEVEL_ONE = "/TextureCreated/rsz_test1.png";

    //Path for tiles sprite
    public static final String TILE_ATLAS = "/Texture/Entities/OakWoods/oak_woods_tileset.png";

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the image from the path
    public static BufferedImage importMap(String path){
        BufferedImage image = null;
        InputStream inputStream = LoadSaveFile.class.getResourceAsStream(path);
        try {
            image = ImageIO.read(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return image;

    }

    //=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get level data
    //hien tai khong sd vi sai
//    public static int[][] getLeveData(){
//        //Create the level data
//        int[][] levelData = new int[TILE_HEIGHT][TILE_WIDTH];
//        BufferedImage image = importMap(LEVEL_ONE);
//        //Define color
//        int value = 0;
//        for (int i = 0; i < image.getHeight(); i ++){
//            for (int j = 0; j < image.getWidth(); j++){
//                Color color = new Color(image.getRGB(j, i));
//                value = color.getRed();
//                if (value >= 315) value = 0;
//                levelData[i][j] = value;
//            }
//        }
//        return levelData;
//    }

}