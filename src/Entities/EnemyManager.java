package Entities;

import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
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
            g.drawImage(crabbyArray[crabby.getEnemyState()][crabby.getAnimationIndex()],
                    (int) crabby.getHitbox().x - xLevelOffset,
                    (int) crabby.getHitbox().y ,
                    CRABBY_WIDTH, CRABBY_HEIGHT, null);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void update(int[][] levelData){
        for (Crabby crabby : crabbyList){
            crabby.update(levelData);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Add crab enemy
    private void addCrabArmy(int[][] levelData){
        crabbyList = Crabby.getCrabArmy(levelData);

    }


}
