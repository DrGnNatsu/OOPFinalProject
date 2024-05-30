package GUI;

import Gamestates.Gamestate;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.Buttons.*;

public class ButtonMenu {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    //Index - for type of button and animation
    private int typeIndex, animationIndex = 1;
    //Position of the button
    private int xPosition, yPosition, xOffsetCenter;
    //Current state of the game
    private Gamestate currentState;
    //Images for the button
    private BufferedImage[] images1D;
    //Mouse over the button
    private boolean mouseOver, mousePressed;
    //Support for the button
    private Rectangle bounds;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ButtonMenu(int xPosition, int yPosition, int typeIndex, Gamestate currentState) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.typeIndex = typeIndex;
        this.currentState = currentState;
        loadImages();
        initializeBounds();
    }

    public ButtonMenu(int xPosition, int yPosition, Gamestate currentState) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.currentState = currentState;
        loadImages();
        initializeBounds();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize the bounds
    private void initializeBounds() {
        bounds = new Rectangle(xPosition - xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Apply game states
    public void applyGameStates() {
        Gamestate.currentState = currentState;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset boolean values
    public void resetBooleanValues() {
        mouseOver = false;
        mousePressed = false;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load images - Using for the name of game
//    private void loadImagesForSameFileTexture() {
//        images = new BufferedImage[2];
//        BufferedImage temporary =
//        for (int i = 0; i < images.length; i++) {
//            images[i] =
//        }
//    }

    //Load Images for the button
    private void loadImages() {
        images1D = new BufferedImage[5];
        BufferedImage tempImage = null;
        String[] name = new String[]{"/Start", "/Settings", "/Quit" };
        String tempString = null;
        switch (typeIndex){
            case 0:
                tempString = LoadSaveFile.BUTTON_MENU + name[0] + name[0];
                break;
            case 1:
                tempString = LoadSaveFile.BUTTON_MENU + name[1] + name[1];
                break;
            case 2:
                tempString = LoadSaveFile.BUTTON_MENU + name[2] + name[2];
                break;
        }
            for (int i = 0 ; i < 5; i++){
                tempImage = LoadSaveFile.importMap(tempString + (i+1) + ".png");
                images1D[i] = tempImage.getSubimage(0, 0,
                        BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
            }

    }

    //Draw the button
    public void draw(Graphics g) {
        g.drawImage(images1D[animationIndex] ,
                xPosition - xOffsetCenter, yPosition,
                BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    //Update the button
    public void update() {
        //Check if the mouse is on the button
        animationIndex = 1;
        if (mouseOver) animationIndex = 3;
        if (mousePressed) animationIndex = 5;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
