package Utilization;

import Entities.Crabby;
import Levels.LevelManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;


public class LoadSaveFile {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Path for the player sprite
    public static final String PLAYER_ATLAS1 = "/Texture/Entities/GenericChar/png/blue/CharBlue1.png";
    public static final String PLAYER_ATLAS2 = "/Texture/Entities/GenericChar/png/blue/CharBlue2.png";

    //Path for crabby sprite
    public static final String CRABBY_ATLAS = "/Texture/Entities/BorrowKaarin/crabby_sprite.png";

    //Path for tiles sprite
    public static final String TILE_ATLAS = "/Texture/Entities/OakWoods/oak_woods_tileset.png";

    //Path for the button on menu
    public static final String BUTTON_MENU = "/Texture/Entities/ButtonPack";

    //Path for the border and inside the menu
    public static final String BORDER_MENU_BACKGROUND= "/Texture/Entities/Border/01Border03.png";
    public static final String INSIDE_MENU_BACKGROUND = "/Texture/Entities/CryoGUI/GUI/GUI_4x.png";

    //Path for background menu
    public static final String BACKGROUND1_LSF = "/Texture/Entities/OakWoods/background/background_layer_1.png";
    public static final String BACKGROUND2_LSF = "/Texture/Entities/OakWoods/background/background_layer_2.png";
    public static final String BACKGROUND3_LSF = "/Texture/Entities/OakWoods/background/background_layer_3.png";

    //Path for pause menu
    public static final String PAUSE_MENU = "/Texture/Entities/BorrowKaarin/pause_menu.png";

    //Path for level completed menu
    public static final String LEVEL_COMPLETED_MENU = "/Texture/Entities/BorrowKaarin/completed_sprite.png";

    //Path for death menu
    public static final String DEATH_MENU = "/Texture/Entities/BorrowKaarin/Death.png";

    //Path for options menu
    public static final String OPTIONS_MENU = "/Texture/Entities/BorrowKaarin/OptionsBackground.png";

    //Path for sound button
    public static final String SOUND_BUTTON = "/Texture/Entities/BorrowKaarin/sound_button.png";

    //Path for unpause, replay, menu button
    public static final String URM_BUTTON = "/Texture/Entities/BorrowKaarin/urm_buttons.png";

    //Path for volume button
    public static final String VOLUME_BUTTON = "/Texture/Entities/BorrowKaarin/volume_buttons.png";

    //Path for playing background
    public static final String PLAYING_BACKGROUND = "/Texture/Entities/BorrowKaarin/playing_bg_img.png";
    public static final String BIG_CLOUDS = "/Texture/Entities/BorrowKaarin/big_clouds.png";
    public static final String SMALL_CLOUDS = "/Texture/Entities/BorrowKaarin/clouds.png";

    //Path for health and energy bar
    public static final String HEALTH = "/Texture/Entities/BorrowKaarin/HealthPowerBar.png";

    //Path for potion and object
    public static final String POTION = "/Texture/Entities/BorrowKaarin/Potions.png";
    public static final String OBJECT = "/Texture/Entities/BorrowKaarin/Object.png";

    //Path for trap
    public static final String TRAP = "/Texture/Entities/BorrowKaarin/Trap.png";

    //Path for cannon
    public static final String CANNON = "/Texture/Entities/BorrowKaarin/Cannon.png";
    public static final String CANNON_BALL = "/Texture/Entities/BorrowKaarin/Ball.png";

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
