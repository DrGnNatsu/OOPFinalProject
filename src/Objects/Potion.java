package Objects;

import Game.Game;

import java.util.ArrayList;

import static Utilization.ConstantVariables.EnemyConstant.CRABBY_HEIGHT_DEFAULT;

public class Potion extends Object{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
        this.doAnimation = true;
        createHitbox(7, 14);
        this.xDrawOffset = (int) (3 * Game.TILE_SCALE);
        this.yDrawOffset = (int) (2 * Game.TILE_SCALE);
        maxHoverOffset = (int) (Game.TILE_SCALE * 10);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        super.updateAnimationTick();
        updateHover();
    }

    //Update hover
    private void updateHover(){
        hoverOffset += 0.05f * Game.TILE_SCALE * hoverDirection;
        if (hoverOffset >= maxHoverOffset || hoverOffset <= 0){
            hoverDirection *= -1;
        }

        if (hoverOffset < 0){
            hoverDirection = 1;
        }

        hitbox.y = y + hoverOffset;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Add potions to map
    public static ArrayList<Potion> loadPotion (int[][] levelData){
        ArrayList<Potion> potions = new ArrayList<>();
        for (int i = 0; i < levelData.length; i++){
            for (int j = 0; j < levelData[i].length ; j++){
                if (levelData[i][j] == 3 || levelData[i][j] == 4){
                    //Get potion
                    potions.add(new Potion(j * Game.TILE_SIZE_SCALE, i * Game.TILE_SIZE_SCALE - CRABBY_HEIGHT_DEFAULT - 1, levelData[i][j] - 3));
                }

            }

        }

        return potions;

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters


}
