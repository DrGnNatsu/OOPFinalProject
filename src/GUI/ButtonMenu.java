package GUI;

import Gamestates.Gamestate;

import java.awt.image.BufferedImage;

public class ButtonMenu {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private int xPosition, yPosition, rowIndex;
    private Gamestate currentState;
    private BufferedImage[] images1D;
    private BufferedImage[][] images2D;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ButtonMenu(int xPosition, int yPosition, int rowIndex, Gamestate currentState) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rowIndex = rowIndex;
        this.currentState = currentState;
        loadImages();
    }

    public ButtonMenu(int xPosition, int yPosition, Gamestate currentState) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.currentState = currentState;

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
        images1D = new BufferedImage[2];
 
    }

}
