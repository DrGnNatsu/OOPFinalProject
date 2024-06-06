package Objects;

import Entities.Player;
import Gamestates.Playing;
import Utilization.ConstantVariables;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilization.ConstantVariables.ObjectConstants.*;
import static Utilization.ConstantVariables.Projectiles.*;
import static Utilization.SupportMethods.*;

public class ObjectManager {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage[] cannonImages = new BufferedImage[7];
    private BufferedImage spikeImages, cannonBallImage;
    private ArrayList<Potion> potionList = new ArrayList<>();
    private ArrayList<Container> containerList = new ArrayList<>();
    private ArrayList<Spike> spikeList = new ArrayList<>();
    private ArrayList<Cannon> cannonList = new ArrayList<>();
    private ArrayList<Projectile> projectileList = new ArrayList<>();

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

        for (Cannon cannon : cannonList){
            cannon.reset();
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load object
    public void loadObject(int[][] levelData){
        potionList = new ArrayList<>(Potion.loadPotion(levelData));
        containerList = new ArrayList<>(Container.loadContainer(levelData));
        spikeList = Spike.loadSpikes(levelData);
        cannonList = Cannon.loadCannon(levelData);
        projectileList.clear();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the images
    private void loadImages() {
        //Load the potion image
        BufferedImage potionSprite = LoadSaveFile.importMap(LoadSaveFile.POTION);
        potionImages = new BufferedImage[2][7];

        for(int i = 0; i < potionImages.length; i++){
            for(int j = 0; j < potionImages[i].length; j++){
                potionImages[i][j] = potionSprite.getSubimage(j * 12, i * 16, 12, 16);
            }
        }

        //Load the container image
        BufferedImage containerSprite = LoadSaveFile.importMap(LoadSaveFile.OBJECT);
        containerImages = new BufferedImage[2][8];

        for(int i = 0; i < containerImages.length; i++){
            for(int j = 0; j < containerImages[i].length ; j++){
                containerImages[i][j] = containerSprite.getSubimage(j * 40, i * 30, 40, 30);
            }
        }

        //Load the spike image
        BufferedImage spikeSprite = LoadSaveFile.importMap(LoadSaveFile.TRAP);

        //Load the cannon image
        BufferedImage cannonSprite = LoadSaveFile.importMap(LoadSaveFile.CANNON);
        for(int i = 0; i < cannonImages.length; i++){
            cannonImages[i] = cannonSprite.getSubimage(i * 40, 0, 40, 26);
        }

        //Load cannon ball image
        cannonBallImage = LoadSaveFile.importMap(LoadSaveFile.CANNON_BALL);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(int[][] levelData, Player player){
        for (Potion potion : potionList){
            if(potion.isActive()) potion.update();
        }

        for (Container container : containerList){
            if(container.isActive()) container.update();
        }

        updateCannon(levelData, player);
        updateProjectiles(levelData, player);
    }

    private void updateProjectiles(int[][] levelData, Player player) {
        for (Projectile projectile : projectileList){
               if (projectile.isActive()) {
                   projectile.updatePosition();
                   if (projectile.getHitbox().intersects(player.getHitbox())){
                       player.changeHealth(-CANNON_BALL_DAMAGE);
                       projectile.setActive(false);
                   }

                   if (isProjectileHitLevel(projectile, levelData)){
                       projectile.setActive(false);
                   }

               }

        }

    }

    private void updateCannon(int[][] levelData, Player player){
        /*
        if the cannon is not animating.
        TileY is same if the player in front of the cannon.
        If the player is in front of the cannon, the cannon will shoot.
         */
        for (Cannon cannon : cannonList){
            if (!cannon.doAnimation && isPlayerInRange(cannon, player)
                    && cannon.getTileY() == player.getTileY() && isPlayerInFrontOfCannon(cannon, player)
                    && canCannonSeePlayer(levelData, player.getHitbox(), cannon.getHitbox(), cannon.getTileY())){
                cannon.setDoAnimation(true);
            }
            cannon.update();

            if (cannon.getAnimationIndex() == 4
                    && cannon.getAnimationTick() == 0) {
                cannonShoot(cannon);
            }

        }

    }

    //Cannon shoot
    private void cannonShoot(Cannon cannon){
        cannon.setDoAnimation(true);
        int direction = 1;
        if (cannon.getObjectType() == CANNON_LEFT){
            direction = -1;
        }

        projectileList.add(new Projectile((int) cannon.getHitbox().x , (int) cannon.getHitbox().y , direction));

    }

    //Check if the player is in front of the cannon
    private boolean isPlayerInFrontOfCannon(Cannon cannon, Player player) {
        return (cannon.getObjectType() == CANNON_LEFT && player.getHitbox().x < cannon.getHitbox().x)
                || (cannon.getObjectType() == CANNON_RIGHT && player.getHitbox().x > cannon.getHitbox().x);
    }

    //Check if the player is in range of the cannon
    private boolean isPlayerInRange(Cannon cannon, Player player) {
        int absValue = (int) Math.abs(cannon.getHitbox().x - player.getHitbox().x);
        return absValue <= 23 * 36;
    }

    //Draw
    public void draw(Graphics g, int xLevelOffset){
        drawPotions(g, xLevelOffset);
        drawContainers(g, xLevelOffset);
        drawSpikes(g, xLevelOffset);
        drawCannons(g, xLevelOffset);
        drawProjectiles(g, xLevelOffset);
    }

    private void drawProjectiles(Graphics g, int xLevelOffset) {
        for (Projectile projectile : projectileList){
            if (projectile.isActive()){
                g.drawImage(cannonBallImage,
                        (int) projectile.getHitbox().x - xLevelOffset,
                        (int) projectile.getHitbox().y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);

            }

        }

    }

    private void drawCannons(Graphics g, int xLevelOffset){
        int x, width ;
        for (Cannon cannon : cannonList){
            x = (int) cannon.getHitbox().x - xLevelOffset - cannon.getXDrawOffset();
            width = CANNON_WIDTH;
            if (cannon.getObjectType() == CANNON_RIGHT){
                x += width;
                width *= -1;
            }

            g.drawImage(cannonImages[cannon.getAnimationIndex()],
                    x, (int) cannon.getHitbox().y - cannon.getYDrawOffset(),
                    CANNON_WIDTH, CANNON_HEIGHT, null);
        }

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
