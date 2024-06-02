package Entities;

import static Utilization.ConstantVariables.EnemyConstant.*;

import static Utilization.ConstantVariables.PlayerConstant.*;

import static Utilization.SupportMethods.*;

public abstract class Enemy extends Entity{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private int enemyState, enemyType;
    private int animationIndex, animationTick, animationSpeed = 20;
    //Falling
    private boolean inAir = false;
    private float fallSpeed;
    private final float gravity = 0.04f ;
    //Walking
    private float walkSpeed = 0.75f;
    private int walkDirection = LEFT;
    //Support
    private boolean firstUpdate = true ;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        createHitbox(x, y, width, height);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update Animation Tick
    private void updateAnimationTick(){
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmountEnemy(enemyType, enemyState)) {
                animationIndex = 0;
            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(int[][] levelData){
        updateAnimationTick();
        updateMovement(levelData);
    }

    //Update movement
    private void updateMovement(int[][] levelData){
        if (firstUpdate && !isEntityOnFloor(hitbox, levelData)){
            inAir = true;
        }
        firstUpdate = false;

        if (inAir){
            if(canMove(hitbox.x, hitbox.y + 1, hitbox.width, hitbox.height, levelData)){
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = getEntityYPositionUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }

        } else {
            switch (enemyState){
                case IDLE_C:
                    enemyState = RUNNING_C;
                    break;
                case RUNNING_C:
                    float xSpeed = 0;
                    if (walkDirection == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;

                    if(canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)
                    && isEntityOnFloor(hitbox, levelData)){
                            hitbox.x += xSpeed;
                            return;
                    }

                    changeWalkDirection();
                    break;

            }

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Change walk direction
    private void changeWalkDirection(){
        if (walkDirection == LEFT) walkDirection = RIGHT;
        else walkDirection = LEFT;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public int getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex) {
        this.animationIndex = animationIndex;
    }

    public int getAnimationTick() {
        return animationTick;
    }

    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

}
