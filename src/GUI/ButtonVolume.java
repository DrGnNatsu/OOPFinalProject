package GUI;

import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.VolumeButton.*;

public class ButtonVolume extends ButtonPause{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private BufferedImage[] volumeButtonImages = new BufferedImage[3];
    private BufferedImage sldier = null;
    //Index
    private int index = 0;
    //Mouse over and mouse pressed
    private boolean mouseOver = false;
    private boolean mousePressed = false;
    //Position
    private int buttonX = 0;
    private int minX, maxX;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ButtonVolume(int x, int y, int width, int height) {
        super(x + width /2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.y = y;
        this.minX = x + VOLUME_WIDTH / 2;
        this.maxX = x + width - VOLUME_WIDTH / 2;
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
        g.drawImage(sldier, x, y, width * 15 / 2, height, null);
        g.drawImage(volumeButtonImages[index], buttonX - VOLUME_WIDTH / 2 , y, width, height, null);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //ChangeX
    public void changeX(int x){
        if (x < minX) x = minX;
        if (x > maxX) x = maxX;
        buttonX = x;

        bounds.x = buttonX - VOLUME_WIDTH / 2;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load images
    private void loadImages(){
        BufferedImage temporary = LoadSaveFile.importMap(LoadSaveFile.VOLUME_BUTTON);
        for (int i = 0; i < 3; i++){
            volumeButtonImages[i] = temporary.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }

        sldier = temporary.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
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

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

}
