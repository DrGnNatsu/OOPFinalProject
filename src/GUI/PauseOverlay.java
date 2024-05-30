package GUI;

import Game.Game;
import Utilization.LoadSaveFile;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.PauseButton.SOUND_SIZE;

public class PauseOverlay {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables for background
    private BufferedImage background = null;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;

    //Variables for sound button
    private ButtonSound musicButton, sfxButton;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public PauseOverlay(){
        loadBackground();
        createSoundButton();
    }



    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load background
    private void loadBackground(){
        background = LoadSaveFile.importMap(LoadSaveFile.PAUSE_MENU);
        backgroundWidth = background.getWidth() * 2;
        backgroundHeight = background.getHeight() * 2;
        backgroundX = (Game.GAME_WIDTH - backgroundWidth) / 2;
        backgroundY = (Game.GAME_HEIGHT - backgroundHeight - 50) / 2;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create sound button
    private void createSoundButton() {
        int soundX = backgroundX + 325;
        int musicY = backgroundY + 240;
        int sfxY = backgroundY + 340;
        musicButton = new ButtonSound(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new ButtonSound(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        musicButton.update();
        sfxButton.update();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(Graphics g){
        //Draw background
        g.drawImage(background, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        //Draw sound button
        musicButton.draw(g);
        sfxButton.draw(g);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseDragged(MouseEvent e){

    }

    public void mousePressed(MouseEvent e) {
        //Set the music button is pressed
        if (isIn(e, musicButton)){
            musicButton.setMousePressed(true);
        }

        //Set the sfx button is pressed
        if (isIn(e, sfxButton)){
            sfxButton.setMousePressed(true);
        }

    }

    public void mouseReleased(MouseEvent e) {
        //If the musicButton was muted, it becomes unmuted and vice versa
        if (isIn(e, musicButton) && musicButton.isMousePressed()){
            musicButton.setMuted(!musicButton.isMuted());
        }

        //If the sfxButton was muted, it becomes unmuted and vice versa
        if (isIn(e, sfxButton) && sfxButton.isMousePressed()){
            sfxButton.setMuted(!sfxButton.isMuted());
        }

        musicButton.resetBoolean();
        sfxButton.resetBoolean();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if(isIn(e, musicButton)){
            musicButton.setMouseOver(true);
        }

        if(isIn(e, sfxButton)){
            sfxButton.setMouseOver(true);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check if the mouse is in the button
    private boolean isIn(MouseEvent e, ButtonPause buttonPause){
        return buttonPause.getBounds().contains(e.getX(), e.getY());
    }

}
