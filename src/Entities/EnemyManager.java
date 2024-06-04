package Entities;

import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilization.ConstantVariables.EnemyConstant.*;

public class EnemyManager {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;
    //Images
    private BufferedImage[][] crabbyArray = new BufferedImage[5][9];
    private ArrayList<Crabby> crabbyList = new ArrayList<>();

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public EnemyManager(Playing playing, int[][] levelData){
        this.playing = playing;
        loadEnemyImages();
        addCrabArmy(levelData);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the images
    private void loadEnemyImages(){
        BufferedImage temporary = LoadSaveFile.importMap(LoadSaveFile.CRABBY_ATLAS);
        for (int i = 0; i < crabbyArray.length; i++){
            for (int j = 0; j < crabbyArray[i].length; j++){
                crabbyArray[i][j] = temporary.getSubimage
                        (j * CRABBY_WIDTH_DEFAULT, i * CRABBY_HEIGHT_DEFAULT,
                                CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void draw(Graphics g, int xLevelOffset){
        for (Crabby crabby : crabbyList){
            if(crabby.isActive()) g.drawImage(crabbyArray[crabby.getEnemyState()][crabby.getAnimationIndex()],
                    (int) crabby.getHitbox().x - xLevelOffset - CRABBY_DRAWOFFSET_X + crabby.flipX(),
                    (int) crabby.getHitbox().y - CRABBY_DRAWOFFSET_Y + 10 ,
                    CRABBY_WIDTH * crabby.flipW(), CRABBY_HEIGHT, null);

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void update(int[][] levelData, Player player){
        for (Crabby crabby : crabbyList){
            if (crabby.isActive()) crabby.update(levelData, player);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Add crab enemy
    private void addCrabArmy(int[][] levelData){
        crabbyList = Crabby.getCrabArmy(levelData);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check enemy hit
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for (Crabby crabby : crabbyList){
            if (attackBox.intersects(crabby.getHitbox()) && crabby.isActive()){
                crabby.hurt(10);
                return;
            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset all enemies
    public void resetAllEnemies(){
        for (Crabby crabby : crabbyList){
            crabby.resetEnemy();
        }

    }

}
