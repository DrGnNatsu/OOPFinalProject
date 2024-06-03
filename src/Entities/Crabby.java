package Entities;

import java.util.ArrayList;

import Game.*;

import static Utilization.ConstantVariables.EnemyConstant.*;


public class Crabby extends Enemy{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        createHitbox(x, y, (int) (CRABBY_WIDTH * Game.PLAYER_SCALE), (int) (CRABBY_HEIGHT * Game.PLAYER_SCALE));
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get crab army
    public static ArrayList<Crabby> getCrabArmy(int[][] levelData){
        ArrayList<Crabby> crabArmy = new ArrayList<>();
        for (int i = 0; i < levelData.length; i++){
            for (int j = 0; j < levelData[i].length ; j++){
                if (levelData[i][j] == 2){
                    //Add the crab
                    crabArmy.add(new Crabby(j * Game.TILE_SIZE_SCALE, i * Game.TILE_SIZE_SCALE - CRABBY_HEIGHT_DEFAULT - 1));
                }
            }
        }

        return crabArmy;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(int[][] levelData, Player player){
        updateAnimationTick();
        updateMovement(levelData, player);
    }

    //Update movement
    private void updateMovement(int[][] levelData, Player player) {
        if (firstUpdate) firstCheckUpdate(levelData);

        if (inAir)
            updateInAir(levelData);
        else {
            switch (enemyState) {
                case IDLE_C:
                    newState(RUNNING_C);
                    break;
                case RUNNING_C:
                    if(canSeePlayer(levelData, player))
                        turnTowardPlayer(player);

                    if(isPlayerCloseToAttack(player))
                        newState(ATTACK_C);

                    move(levelData);
                    break;

            }

        }

    }

}
