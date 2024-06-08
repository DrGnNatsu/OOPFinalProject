package GUI;

import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.URMButton.*;

public class ButtonURM extends ButtonPause{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private BufferedImage[] buttonURMImages= new BufferedImage[3];
    //Index for the button
    private int rowIndex, index;
    //Mouse
    private boolean mouseOver = false, mousePressed = false;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ButtonURM(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        index = 0;
        if (mouseOver) index = 1;
        if (mousePressed) index = 2;

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(Graphics g){
        g.drawImage(buttonURMImages[index], x, y, URM_BUTTON_SIZE, URM_BUTTON_SIZE, null);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load images
    private void loadImages(){
        BufferedImage temporary = LoadSaveFile.importMap(LoadSaveFile.URM_BUTTON);
        for (int i = 0; i < buttonURMImages.length; i++) {
            buttonURMImages[i] = temporary.getSubimage(i * URM_BUTTON_SIZE_DEFAULT,
                    rowIndex * URM_BUTTON_SIZE_DEFAULT,
                    URM_BUTTON_SIZE_DEFAULT, URM_BUTTON_SIZE_DEFAULT);
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset booleans values
    public void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

}
