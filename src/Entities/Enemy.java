package Entities;

import java.awt.geom.Rectangle2D;

import static Game.Game.*;
import static Utilization.ConstantVariables.EnemyConstant.*;
import static Utilization.ConstantVariables.*;
import static Utilization.ConstantVariables.PlayerConstant.*;


import static Utilization.SupportMethods.*;

public abstract class Enemy extends Entity{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    protected int state, enemyType;

    //Falling
    protected final float gravity = 0.04f ;

    //Walking
    protected int walkDirection = LEFT;

    //Attack
    protected int tileY;
    protected float attackDistance = TILE_SIZE;

    //Support
    protected boolean firstUpdate = true ;
    protected boolean active = true;
    protected boolean attackChecked;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.walkSpeed = 0.75f;
        createHitbox(width, height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update Animation Tick
    protected void updateAnimationTick(){
        animationTick++;
        if (animationTick >= ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmountEnemy(enemyType, state)) {
                animationIndex = 0;
                switch(state){
                    case ATTACK_C, HIT_C -> state = IDLE_C;
                    case DEATH_C -> active = false;
                }

            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //First check update
    protected void firstCheckUpdate(int[][] levelData){
        if (!isEntityOnFloor(hitbox, levelData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update in air
    protected void updateInAir(int[][] levelData){
        if (canMove(hitbox.x, hitbox.y + 1, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / TILE_SIZE);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Change walk direction
    protected void changeWalkDirection(){
        if (walkDirection == LEFT) walkDirection = RIGHT;
        else walkDirection = LEFT;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Move
    protected void move(int[][] levelData){
        float xSpeed = 0;
        if (walkDirection == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)
                && isFloor(hitbox, levelData, xSpeed)) {
            hitbox.x += xSpeed;
            return;
        }

        changeWalkDirection();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Detect player
    protected boolean canSeePlayer(int[][] levelData, Player player){
        int playerTileY = (int) (player.getHitbox().y / TILE_SIZE_SCALE);

        if((playerTileY == tileY) && (isPlayerInRange(player))
                && (isSightClear(levelData, hitbox, player.getHitbox(), tileY))){
            return true;
        }

        return false;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Turn toward to player
    protected void turnTowardPlayer(Player player){
        if (player.getHitbox().x < hitbox.x + hitbox.width / 2)
            walkDirection = LEFT;
        else
            walkDirection = RIGHT;

    }

    //Player in range
    protected boolean isPlayerInRange(Player player){
        int absValue = Math.abs((int) (player.getHitbox().x - hitbox.x));
        return absValue <= attackDistance * 3;
    }

    //Player close to attack
    protected boolean isPlayerCloseToAttack(Player player){
        int absValue = Math.abs((int) (player.getHitbox().x - hitbox.x));
        return absValue <= attackDistance;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Hit
    public void hurt(int amount){
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEATH_C);
        }
        else newState(HIT_C);
    }

    //Check if the player is close to attack
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects(player.getHitbox())){
            player.changeHealth(-getEnemyDamage(enemyType));
        }
        attackChecked = true;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Change state
    protected void newState(int state){
        this.state = state;
        animationIndex = 0;
        animationTick = 0;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset all
    public void resetEnemy(){
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE_C);
        active = true;
        airSpeed = 0;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public boolean isActive() {
        return active;
    }

}
