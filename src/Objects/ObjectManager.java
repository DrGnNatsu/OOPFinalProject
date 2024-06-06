package Objects;

import Entities.Player;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilization.ConstantVariables.ObjectConstants.*;

public class ObjectManager {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage spikeImages;
    private ArrayList<Potion> potionList = new ArrayList<>();
    private ArrayList<Container> containerList = new ArrayList<>();
    private ArrayList<Spike> spikeList = new ArrayList<>();

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check spikes touched
    public void checkSpikesTouched(Player player){
        for (Spike spike : spikeList){
            if (player.getHitbox().intersects(spike.getHitbox())){
                player.kill();
                break;
            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check object touched
    public void checkPotionTouched(Rectangle2D.Float hitbox){
        for (Potion potions: potionList){
            if (potions.isActive() && hitbox.intersects(potions.getHitbox())){
                potions.setActive(false);
                applyEffect(potions);
                break;
            }

        }

    }

    //Apply effect to player
    public void applyEffect(Potion potion){
        switch (potion.getObjectType()){
            case RED_POTION -> playing.getPlayer().changeHealth(RED_POTION_VALUE);
            case BLUE_POTION -> playing.getPlayer().changePower(BLUE_POTION_VALUE);
        }

    }

    //Check object hit
    public void checkObjectHit(Rectangle2D.Float attackBox){
        int type;
        for (Container container: containerList){
            if (container.isActive() && container.getHitbox().intersects(attackBox) && !container.isHit()){
                container.setDoAnimation(true);
                container.setHit(true);
                type = 0;
                if(container.getObjectType() == BARREL){
                    type = 1;
                }
                potionList.add(new Potion((int) (container.getHitbox().x + container.getHitbox().width / 2),
                        (int) container.getHitbox().y , type));

                return;

            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset all objects
    public void resetAllObjects(){
        loadObject(playing.getLevelManager().getCurrentLevel());

        for (Potion potion : potionList){
            potion.reset();
        }

        for (Container container : containerList){
            container.reset();
        }

        for (Spike spike : spikeList){
            spike.reset();
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load object
    public void loadObject(int[][] levelData){
        potionList = new ArrayList<>(Potion.loadPotion(levelData));
        containerList = new ArrayList<>(Container.loadContainer(levelData));
        spikeList = Spike.loadSpikes(levelData);
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

        BufferedImage spikeSprite = LoadSaveFile.importMap(LoadSaveFile.TRAP);
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
        drawSpikes(g, xLevelOffset);
    }

    private void drawSpikes(Graphics g, int xLevelOffset){
        for (Spike spike : spikeList){
            g.drawImage(spikeImages,
                    (int) spike.getHitbox().x - xLevelOffset - spike.getXDrawOffset(),
                    (int) spike.getHitbox().y - spike.getYDrawOffset(),
                    SPIKE_WIDTH, SPIKE_HEIGHT, null);

        }

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
