package Entities;

import Audio.AudioPlayer;
import Game.Game;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.*;
import static Utilization.ConstantVariables.PlayerConstant.*;
import static Utilization.SupportMethods.*;


public class Player extends Entity{
    //Create the variables for the animations
    private Playing playing;
    private final BufferedImage[][] animation = new BufferedImage[11][8];
    private BufferedImage image;

    //Player
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Define player direction
    private boolean left, right, jump;

    //Define player status
    private boolean playerMoving = false;
    private boolean attack = false;


    //Define level data
    private int[][] levelData;

    //Create variables for the hitbox
    private final float xDrawOffset = 16 * Game.PLAYER_SCALE;
    private final float yDrawOffset = 24 * Game.PLAYER_SCALE;

    //Create variables for  gravity
    private final float jumpSpeed = -0.7f * Game.PLAYER_SCALE; // jump height
    private final float fallSpeedAfterJump = 0.2f * Game.PLAYER_SCALE;

    //Attack Box
    private int flipX = 0;
    private int flipWidth = 1;
    private boolean attackChecked;

    //Status bar UI
    private BufferedImage statusBarImage;
    private final int statusBarWidth = 330;
    private final int statusBarHeight = 110;
    private final int statusBarX = 20;
    private final int statusBarY = 20;

    ///Health
    private final int healthBarWidth = 265;
    private final int healthBarHeight = 10;
    private final int healthBarX = 55;
    private final int healthBarY = 25;
    private int healthWidth = healthBarWidth;

    //Energy
    private final int powerBarWidth = 182;
    private final int powerBarHeight = 5;
    private final int powerBarX = 73;
    private final int powerBarY = 63;
    private int powerWidth = powerBarWidth;
    private final int powerMaxValue = 200;
    private int powerValue = powerMaxValue;

    //Tile Y
    private int tileY = 0;

    //Power attack
    private boolean powerAttackActive = false;
    private int powerAttackTick;
    private int powerGrowSpeed = 0; //Increase power by the time
    private int powerGrowTick;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Player(float x, float y, int width , int height, Playing playing){
        //Call the constructor from the Entity class
        super(x, y, width, height);
        this.playing = playing;
        //Set the max health
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        //Set the walk speed
        this.walkSpeed = 1f;
        //Import the image with the right file sprite.
        chooseImage();
        //Create the hitbox
        createHitbox(20 * Game.PLAYER_SCALE, 32 * Game.PLAYER_SCALE);
        //Load the animation
        loadAnimation();
        //Create the attack box
        createAttackBox();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update the player
    public void update(){
        updateHealthBar();
        updatePowerBar();

        if (currentHealth <= 0) {
            if (state != DEATH) {
                state = DEATH;
                resetAnimationTick();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
                playing.setPlayerDeath(true);
            }

            if((animationIndex == getSpriteAmount(DEATH) - 1) && animationTick >= ANIMATION_SPEED - 1){
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateAnimation();
            }

            return;
        }

        setAnimation();
        updatePosition();
        if(playerMoving) {
            checkPotionTouched();
            checkSpikesTouched();
            tileY = (int) (hitbox.y / 36);

            if(powerAttackActive){
                powerAttackTick++;
                if (powerAttackTick >= 35) {
                    powerAttackActive = false;
                    powerAttackTick = 0;
                }

            }

        }

        if (attack || powerAttackActive) checkAttack();
        updateAnimation();
        updateAttackBox();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Render the player
    public void renderAnimations(Graphics g, int levelOffset){
        //Draw the player
        g.drawImage(animation[state][animationIndex],
                (int) (hitbox.x - xDrawOffset) - levelOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                width * flipWidth, height, null);

        //Draw the status bar
        drawUI(g);

    }

    private void drawUI(Graphics g) {
        //Image status bar
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        //Health bar
        g.setColor(Color.RED);
        g.fillRect(healthBarX + statusBarX, healthBarY + statusBarY, healthWidth, healthBarHeight);

        g.setColor(Color.ORANGE);
        g.fillRect(powerBarX + statusBarX, powerBarY + statusBarY, powerWidth, powerBarHeight);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Player
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Sets the player direction
    private void setAnimation() {
        int startAnimation = state;

        if (playerMoving) state = RUN;
        else state = IDLE;

        if (inAir) {
            if (airSpeed < 0) state = JUMP;
            else state = LAND;
        }

        if (powerAttackActive) {
            state = ATTACK;
            animationIndex = 6;
            animationTick = 0;
            return;
        }

        if (attack) {
            state = ATTACK;
            //Reset the animation tick
            if (startAnimation != ATTACK){
                animationIndex = 1;
                animationTick = 0;
                return;
            }

        }

        if (startAnimation != state) resetAnimationTick();
    }

    private void updatePosition() {
        //Check if the player is moving
        playerMoving = false;

        //Check if the player is jumping
        if (jump) jumpMethod();

        //Check if the player is moving
        if (!inAir && !powerAttackActive && (!left && !right) || (left && right)){
             return;
        }

        //Set the walk speed of the player
        float xSpeed = 0;

        //Move the player left and right
        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipWidth = -1;
        }

        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipWidth = 1;
        }

        //Check power attack
        if (powerAttackActive) {
            if (!left && !right){
                if (flipWidth == -1) xSpeed = -walkSpeed;
                else xSpeed = walkSpeed;
            }

            xSpeed *= 3;
        }

        //Check
        if (!inAir && isEntityOnFloor(hitbox, levelData)) inAir = true;

        //Check if the player is jumping
        if (inAir && !powerAttackActive) {
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
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
        else {
            hitbox.x = getEntityXPositionNextToWall(hitbox, xSpeed);
            //Not collide the wall
            if (powerAttackActive){
                powerAttackActive = false;
                powerAttackTick = 0;
            }

        }

    }

    //Reset the inAir
    private void resetInAir(){
        inAir = false;
        airSpeed = 0;
    }

    //Jump method
    private void jumpMethod(){
        if(inAir) return;
        //Audio player
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        inAir = true;
        airSpeed = jumpSpeed;
    }

    //Reset the player direction
    public void resetDirection(){
        left = false;
        right = false;
    }

    //Update the health bar
    private void updateHealthBar() {
        healthWidth = (int) ((float) currentHealth / maxHealth * healthBarWidth);
    }

    //Update the power bar
    private void updatePowerBar() {
        powerWidth = (int) ((float) powerValue / powerMaxValue * powerBarWidth);
        powerGrowTick++;
        if (powerGrowTick >= powerGrowSpeed) {
            powerGrowTick = 0;
            changePower(1);
        }

    }

    //Change health
    public void changeHealth(int change){
        currentHealth += change;

        if (currentHealth >= maxHealth) currentHealth = maxHealth;

        if (currentHealth <= 0) {
            currentHealth = 0;

        }

    }

    //Change power
    public void changePower(int change){
        powerValue += change;

        if (powerValue >= powerMaxValue) powerValue = powerMaxValue;

        if (powerValue <= 0) {
            powerValue = 0;
            powerAttackActive = false;
        }

    }

    //Power attack
public void powerAttack(){
        if (powerAttackActive) return;

        if (powerValue >= 150) {
            powerAttackActive = true;
            changePower(-150);

        }

    }

    //Check potion touched
    private void checkPotionTouched() {
        playing.checkPotionTouched(this.hitbox);
    }

    //Check spikes touched
    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    //Check attack
    private void checkAttack() {
        //if(attackChecked || animationIndex != 3 || animationIndex != 6) return;
        attackChecked = true;
        //Power attack
        if (powerAttackActive) {
            attackChecked = false;
        }

        //Check if the attack hits the enemy
        playing.checkEnemyHit(this.attackBox);
        playing.checkObjectHit(this.attackBox);
        //Audio player
        //playing.getGame().getAudioPlayer().playAttackSound();
    }

    //Create the attack box
    private void createAttackBox(){
        this.attackBox = new Rectangle2D.Float(x, y, 20 * Game.PLAYER_SCALE, 22 * Game.PLAYER_SCALE);
    }

    //Update attack box
    private void updateAttackBox(){
        this.attackBox.y = hitbox.y + 6 * Game.TILE_SCALE;
        if (right || (powerAttackActive && flipWidth == 1)) {
            this.attackBox.x = hitbox.x + hitbox.width + 7 * Game.TILE_SCALE;
        } else if (left || (powerAttackActive && flipWidth == -1)) {
            this.attackBox.x = hitbox.x - hitbox.width - 7 * Game.TILE_SCALE;
        }

    }

    //Kill
    public void kill() {
        currentHealth = 0;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset all things
    public void resetAll(){
        currentHealth = maxHealth;
        healthWidth = healthBarWidth;
        state = IDLE;
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
        if (animationTick > ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(state)) {
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
        if (state <= 10) {
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

    public boolean isPlayerMoving() {
        return playerMoving;
    }

    public void setPlayerMoving(boolean playerMoving) {
        this.playerMoving = playerMoving;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getState() {
        return state;
    }

    public void setstate(int state) {
        this.state = state;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isJump() {
        return jump;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }


}
