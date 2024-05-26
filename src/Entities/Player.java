package Entities;

import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;


import static Utilization.ConstantVariables.PlayerConstant.*;

public class Player extends Entity{
    //Create the variables for the animations
    private final BufferedImage[][] animation = new BufferedImage[11][8];
    private BufferedImage image;
    //Create the animation tick to change animation
    private int animationTick, animationIndex;
    private final int animationSpeed = 36; // 36 frames per second
    //Define player action
    private int playerAction = IDLE;
    //Define player direction
    private boolean left, right, up, down;
    //Define player status
    private boolean playerMoving = false;
    private boolean attack = false;
    private boolean defense = false;
    //Define the player attributes
    private final float speed = 1.0f;

    public Player(float x, float y){
        //Call the constructor from the Entity class
        super(x, y);
        //Import the image with the right file sprite.
        chooseImage();
        //Load the animation
        loadAnimation();
    }

    public void update(){
        setAnimation();
        updatePosition();
        updateAnimation();
    }

    public void renderAnimations(Graphics g){
        g.drawImage(animation[playerAction][animationIndex], (int) x, (int) y, 56 * 2, 56 * 2, null);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Sets the player direction
    private void setAnimation(){
        int startAnimation = playerAction;

        if (playerMoving)  playerAction = RUN;
        else playerAction = IDLE;

        if (attack) playerAction = ATTACK;
        if (defense) playerAction = DEFENSE;

        if (startAnimation != playerAction) resetAnimationTick();
    }

    private void updatePosition() {
        //Move the player left and right
        if (left && !right) {
            x -= speed;
            playerMoving = true;
        }
        if (right && !left) {
            x += speed;
            playerMoving = true;
        }
        //Move the player up and down
        if (up && !down) {
            y -= speed;
            playerMoving = true;
        }
        if (down && !up) {
            y += speed;
            playerMoving = true;
        }
    }
    //Reset the player direction
    public void resetDirection(){
        left = false;
        right = false;
        up = false;
        down = false;
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
        this.image = LoadSaveFile.GetSpriteAtlas(path);
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

    public void setDefense(boolean defense) {
        this.defense = defense;
    }

    public int getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(int playerAction) {
        this.playerAction = playerAction;
    }
}
