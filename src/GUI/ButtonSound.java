package GUI;

import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.PauseButton.SOUND_SIZE_DEFAULT;

public class ButtonSound extends ButtonPause {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables for stored images
    private BufferedImage[][] soundImages = new BufferedImage[2][3];

    //Variables for detecting mouse
    private boolean mouseOver = false;
    private boolean mousePressed = false;
    private boolean muted = false;
    private int rowIndex, columnIndex; //0 for music, 1 for sfx

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public ButtonSound(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImages();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load sound Images
    private void loadSoundImages(){
        BufferedImage temporary = LoadSaveFile.importMap(LoadSaveFile.SOUND_BUTTON);
        for (int i = 0; i < soundImages.length; i++) {
            for(int j = 0; j < soundImages[i].length; j++) {
                soundImages[i][j] = temporary.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        //Check to choose the image
        if (muted) rowIndex = 1;
        else rowIndex = 0;

        //Check to choose sprite
        columnIndex = 0;
        if (mouseOver) columnIndex = 1;
        else columnIndex = 2;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(Graphics g){
        g.drawImage(soundImages[rowIndex][columnIndex], x, y, width, height, null);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset Boolean values
    public void resetBoolean(){
        mouseOver = false;
        mousePressed = false;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

}
