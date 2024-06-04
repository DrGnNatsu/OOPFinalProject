package Entities;

import Game.Game;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.PlayerConstant.*;
import static Utilization.SupportMethods.*;


public class Player extends Entity{
    //Create the variables for the animations
    private Playing playing;
    private final BufferedImage[][] animation = new BufferedImage[11][8];
    private BufferedImage image;
    //Create the animation tick to change animation
    private int animationTick, animationIndex;
    private final int animationSpeed = 36; // 36 frames per second

    //Player
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Define player action
    private int playerAction = IDLE;
    //Define player direction
    private boolean left, right, up, down, jump;
    //Define player status
    private boolean playerMoving = false;
    private boolean attack = false;
    //Define the player attributes
    private final float speed = 1.0f;
    //Define level data
    private int[][] levelData;
    //Create variables for the hitbox
    private final float xDrawOffset = 16 * Game.PLAYER_SCALE;
    private final float yDrawOffset = 24 * Game.PLAYER_SCALE;
    //Create variables for  gravity
    private float airSpeed = 0f;
    private final float gravity = 0.005f * Game.PLAYER_SCALE;
    private final float jumpSpeed = -1f * Game.PLAYER_SCALE;
    private final float fallSpeedAfterJump = 0.2f * Game.PLAYER_SCALE;
    private boolean inAir = false;
    //Attack Box
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipWidth = 1;
    private boolean attackChecked;

    //Status bar UI - health and energy
    private BufferedImage statusBarImage;
    private final int statusBarWidth = 330;
    private final int statusBarHeight = 110;
    private final int statusBarX = 20;
    private final int statusBarY = 20;
    private final int healthBarWidth = 265;
    private final int healthBarHeight = 10;
    private final int healthBarX = 55;
    private final int healthBarY = 25;
    //Status
    private final int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Player(float x, float y, int width , int height, Playing playing){
        //Call the constructor from the Entity class
        super(x, y, width, height);
        this.playing = playing;
        //Import the image with the right file sprite.
        chooseImage();
        //Create the hitbox
        createHitbox(x, y, 20 * Game.PLAYER_SCALE, 32 * Game.PLAYER_SCALE);
        //Load the animation
        loadAnimation();
        //Create the attack box
        createAttackBox();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update the player
    public void update(){
        updateHealthBar();
        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        setAnimation();
        updatePosition();
        if (attack) checkAttack();
        updateAnimation();
        updateAttackBox();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Render the player
    public void renderAnimations(Graphics g, int levelOffset){
        //Draw the player
        g.drawImage(animation[playerAction][animationIndex],
                (int) (hitbox.x - xDrawOffset) - levelOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                width * flipWidth, height, null);

        //Draw the status bar
        drawUI(g);

    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(healthBarX + statusBarX, healthBarY + statusBarY, healthWidth, healthBarHeight);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Player
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Sets the player direction
    private void setAnimation() {
        int startAnimation = playerAction;

        if (playerMoving) playerAction = RUN;
        else playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0) playerAction = JUMP;
            else playerAction = LAND;
        }

        if (attack) {
            playerAction = ATTACK;
            //Reset the animation tick
            if (startAnimation != ATTACK){
                animationIndex = 1;
                animationTick = 0;
                return;
            }

        }

        if (startAnimation != playerAction) resetAnimationTick();
    }

    private void updatePosition() {
        //Check if the player is moving
        playerMoving = false;

        //Check if the player is jumping
        if (jump) jumpMethod();

        //Check if the player is moving
        if (!inAir)
            if((!left && !right) || (left && right))
                return;

        //Set the speed of the player
        float xSpeed = 0;

        //Move the player left and right
        if (left) {
            xSpeed -= speed;
            flipX = width;
            flipWidth = -1;
        }

        if (right) {
            xSpeed += speed;
            flipX = 0;
            flipWidth = 1;
        }

        //Check
        if (!inAir && isEntityOnFloor(hitbox, levelData)) inAir = true;

        //Check if the player is jumping
        if (inAir) {
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xSpeed);
            } else {
                hitbox.y = getEntityYPositionUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) resetInAir();
                else airSpeed = fallSpeedAfterJump;
                updateXPosition(xSpeed);
            }
        } else updateXPosition(xSpeed);

        playerMoving = true;

    }

    //Update x position
    private void updateXPosition(float xSpeed){
        if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
            hitbox.x += xSpeed;
        else hitbox.x = getEntityXPositionNextToWall(hitbox, xSpeed);
    }

    //Reset the inAir
    private void resetInAir(){
        inAir = false;
        airSpeed = 0;
    }

    //Jump method
    private void jumpMethod(){
        if(inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    //Reset the player direction
    public void resetDirection(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    //Update the health bar
    private void updateHealthBar() {
        healthWidth = (int) ((float) currentHealth / maxHealth * healthBarWidth);
    }

    //Change health
    public void changeHealth(int change){
        currentHealth += change;

        if (currentHealth >= maxHealth) currentHealth = maxHealth;
        if (currentHealth <= 0) {
            currentHealth = 0;
            //gameOver();
        }

    }

    //Check attack
    private void checkAttack() {
        //if(attackChecked || animationIndex != 3 || animationIndex != 6) return;
        attackChecked = true;
        //Check if the attack hits the enemy
        playing.checkEnemyHit(attackBox);
    }

    //Create the attack box
    private void createAttackBox(){
        attackBox = new Rectangle2D.Float(x, y, 20 * Game.PLAYER_SCALE, 22 * Game.PLAYER_SCALE  );
    }

    //Update attack box
    private void updateAttackBox(){
        attackBox.y = hitbox.y + 6 * Game.TILE_SCALE;
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + 7 * Game.TILE_SCALE;
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - 7 * Game.TILE_SCALE;
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset all things
    public void resetAll(){
        currentHealth = maxHealth;
        healthWidth = healthBarWidth;
        playerAction = IDLE;
        playerMoving = false;
        attack = false;
        resetDirection();
        resetAnimationTick();
        resetInAir();
        hitbox.x = 0;
        hitbox.y = 0;

        if (!isEntityOnFloor(hitbox, levelData)) inAir = true;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update the animation
    private void updateAnimation() {
        animationTick++;
        if (animationTick > animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attack = false;
                attackChecked = false;
            }
        }
    }
    //Reset the animation tick
    private void resetAnimationTick(){
        animationTick = 0;
        animationIndex = 0;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the animation
    private void loadAnimation() {
        for (int i = 0; i < animation.length; i++) {
            for (int j = 0; j < animation[i].length; j++) {
                animation[i][j] = image.getSubimage(j * 56, i * 56, 56, 56);
            }
        }

        statusBarImage = LoadSaveFile.importMap(LoadSaveFile.HEALTH);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get information of level data
    public void getLevelData(int[][] levelData){
        this.levelData = levelData;
        if(isEntityOnFloor(hitbox, levelData)) inAir = true;
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Choose image to load
    private void chooseImage(){
        if (playerAction <= 10) {
            importImage(LoadSaveFile.PLAYER_ATLAS1);
        } else {
            importImage(LoadSaveFile.PLAYER_ATLAS2);
        }
    }

    //Import the image
    private void importImage(String path) {
        this.image = LoadSaveFile.importMap(path);
        loadAnimation();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isPlayerMoving() {
        return playerMoving;
    }

    public void setPlayerMoving(boolean playerMoving) {
        this.playerMoving = playerMoving;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(int playerAction) {
        this.playerAction = playerAction;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isJump() {
        return jump;
    }

}
