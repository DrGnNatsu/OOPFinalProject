package Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Game.*;
import Levels.Level;

import static Utilization.ConstantVariables.EnemyConstant.*;
import static Utilization.ConstantVariables.PlayerConstant.*;


public class Crabby extends Enemy{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    //Attack box
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        createHitbox(CRABBY_WIDTH - 80, CRABBY_HEIGHT - 20);
        createAttackBox();

    }

    //crate attack box
    private void createAttackBox(){
        attackBox = new Rectangle2D.Float(x, y, 82 * 2,  19 * 2);
        attackBoxOffsetX = 30 * 2;
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
        updateBehavior(levelData, player);
        updateAttackBox();
    }

    //Update behavior
    private void updateBehavior(int[][] levelData, Player player) {
        if (firstUpdate) firstCheckUpdate(levelData);

        if (inAir)
            updateInAir(levelData);
        else {
            switch (this.state) {
                case IDLE_C:
                    newState(RUNNING_C);
                    break;
                case RUNNING_C:
                    if(canSeePlayer(levelData, player)) turnTowardPlayer(player);

                    if(isPlayerCloseToAttack(player)) newState(ATTACK_C);

                    move(levelData);
                    break;
                case ATTACK_C:
                    if (animationIndex == 0) attackChecked = false;
                    if (animationIndex == 3 && !attackChecked){
                        checkPlayerHit(attackBox, player);
                    }
                    break;

                case HIT_C:
                    break;

            }

        }

    }

    //update attack box
    private void updateAttackBox(){
        attackBox.x = hitbox.x - attackBoxOffsetX + 5;
        attackBox.y = hitbox.y + 15;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Flip
    public int flipX(){
        return walkDirection == RIGHT ? width : 0;
    }

    public int flipW(){
        return walkDirection == RIGHT ? -1 : 1;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //draw
    public void drawAttackBox(Graphics g, int xLevelOffset){
        g.setColor(Color.RED);
        g.drawRect((int) (attackBox.x - xLevelOffset + 5), (int) attackBox.y +15, (int) attackBox.width, (int) attackBox.height);
    }

}
