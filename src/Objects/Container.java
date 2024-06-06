package Objects;

import Game.Game;

import java.util.ArrayList;

import static Utilization.ConstantVariables.EnemyConstant.CRABBY_HEIGHT_DEFAULT;
import static Utilization.ConstantVariables.ObjectConstants.*;


public class Container extends Object{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private boolean isHit = false;
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Container(int x, int y, int objectType) {
        super(x, y, objectType);
        initializeHitbox();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        if(doAnimation)
            super.updateAnimationTick();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create hitbox
    private void initializeHitbox(){
        if (objectType == BOX){
            createHitbox(25, 18);
            this.xDrawOffset = (int) (7 * Game.TILE_SCALE);
            this.yDrawOffset = (int) (12 * Game.TILE_SCALE);
        } else {
            createHitbox(23, 25);
            this.xDrawOffset = (int) (8 * Game.TILE_SCALE);
            this.yDrawOffset = (int) (5 * Game.TILE_SCALE);
        }

        hitbox.y += yDrawOffset - 2;
        hitbox.x += xDrawOffset / 2;

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load container
    public static ArrayList<Container> loadContainer (int[][] levelData) {
        ArrayList<Container> containers = new ArrayList<>();
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length; j++) {
                if (levelData[i][j] == 5 || levelData[i][j] == 6) {
                    //Get potion
                    containers.add(new Container(j * Game.TILE_SIZE_SCALE, i * Game.TILE_SIZE_SCALE - CRABBY_HEIGHT_DEFAULT - 1, levelData[i][j] - 3));
                }

            }

        }

        return containers;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
