package Utilization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class LoadSaveFile {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Path for the player sprite
    public static final String PLAYER_ATLAS1 = "/Texture/Entities/GenericChar/png/blue/CharBlue1.png";
    public static final String PLAYER_ATLAS2 = "/Texture/Entities/GenericChar/png/blue/CharBlue2.png";
    //Path for background
    public static final String LEVEL_ATLAS_BACKGROUND = "/TextureCreated/Background.png";

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the image from the path
    public static BufferedImage GetSpriteAtlas(String path){
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

}
