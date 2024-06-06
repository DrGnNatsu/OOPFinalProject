package Objects;

import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilization.ConstantVariables.ObjectConstants.*;

public class ObjectManager {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private ArrayList<Potion> potionList = new ArrayList<>();
    private ArrayList<Container> containerList = new ArrayList<>();


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();

        potionList.add(new Potion(100, 100, RED_POTION));
        potionList.add(new Potion(200, 100, BLUE_POTION));

        containerList.add(new Container(300, 100, BARREL));
        containerList.add(new Container(400, 100, BOX));

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load object
    public void loadObject(int[][] levelData){
        potionList = Potion.loadPotion(levelData);
        containerList = Container.loadContainer(levelData);
    }
    
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the images
    private void loadImages() {
        BufferedImage potionSprite = LoadSaveFile.importMap(LoadSaveFile.POTION);
        potionImages = new BufferedImage[2][7];

        for(int i = 0; i < potionImages.length; i++){
            for(int j = 0; j < potionImages[i].length; j++){
                potionImages[i][j] = potionSprite.getSubimage(j * 12, i * 16, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSaveFile.importMap(LoadSaveFile.OBJECT);
        containerImages = new BufferedImage[2][8];

        for(int i = 0; i < containerImages.length; i++){
            for(int j = 0; j < containerImages[i].length ; j++){
                containerImages[i][j] = containerSprite.getSubimage(j * 40, i * 30, 40, 30);
            }
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        for (Potion potion : potionList){
            if(potion.isActive()) potion.update();
        }

        for (Container container : containerList){
            if(container.isActive()) container.update();
        }
    }

    //Draw
    public void draw(Graphics g, int xLevelOffset){
        drawPotions(g, xLevelOffset);
        drawContainers(g, xLevelOffset);
    }

    private void drawPotions(Graphics g, int xLevelOffset){
        for (Potion potion : potionList){
            if(potion.isActive()){
                int type = 0;
                if (potion.getObjectType() == RED_POTION) {
                    type = 1;
                }

                g.drawImage(potionImages[type][potion.getAnimationIndex()],
                        (int) potion.getHitbox().x - xLevelOffset - potion.getXDrawOffset(),
                        (int) potion.getHitbox().y - potion.getYDrawOffset(),
                        POTION_WIDTH, POTION_HEIGHT, null);

            }

        }

    }

    private void drawContainers(Graphics g, int xLevelOffset){
        for (Container container : containerList){
            if(container.isActive()){
                int type = 0;
                if (container.getObjectType() == BARREL) {
                    type = 1;
                }

                g.drawImage(containerImages[type][container.getAnimationIndex()],
                        (int) container.getHitbox().x - xLevelOffset - container.getXDrawOffset(),
                        (int) container.getHitbox().y - container.getYDrawOffset(),
                        CONTAINER_WIDTH, CONTAINER_HEIGHT, null);

            }

        }

    }

}
