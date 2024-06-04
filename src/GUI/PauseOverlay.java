package GUI;

import Game.Game;
import Gamestates.Gamestate;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.PauseButton.SOUND_SIZE;
import static Utilization.ConstantVariables.GUI.URMButton.URM_BUTTON_SIZE;
import static Utilization.ConstantVariables.GUI.VolumeButton.SLIDER_WIDTH;
import static Utilization.ConstantVariables.GUI.VolumeButton.VOLUME_HEIGHT;

public class PauseOverlay {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables for background
    private BufferedImage background = null;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;

    //Variables for sound button
    private ButtonSound musicButton, sfxButton;

    //Variables for URM button
    private ButtonURM menuButton, replayButton, unpauseButton;

    //Varibles for volume button
    private ButtonVolume volumeButton;

    //Variables for the playing gamestate
    private Playing playing;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public PauseOverlay(Playing playing){
        this.playing = playing;
        loadBackground();
        createSoundButton();
        createURMbButton();
        createVolumeButton();
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

    //Create URM button
    private void createURMbButton() {
        //Supporting variables
        int buttonOffset = 100;
        int separation = 40;

        //Variables for URM button
        int buttonY = backgroundY + 610;

        int replayX = (Game.GAME_WIDTH - URM_BUTTON_SIZE) / 2;
        int unpauseX = replayX + URM_BUTTON_SIZE + separation;
        int menuX = replayX - URM_BUTTON_SIZE - separation;

        //Create the URM buttons
        unpauseButton = new ButtonURM(unpauseX, buttonY, URM_BUTTON_SIZE, URM_BUTTON_SIZE, 0);
        replayButton = new ButtonURM(replayX, buttonY, URM_BUTTON_SIZE, URM_BUTTON_SIZE, 1);
        menuButton = new ButtonURM(menuX, buttonY, URM_BUTTON_SIZE, URM_BUTTON_SIZE, 2);

    }

    //Create volume button
    private void createVolumeButton(){
        int volumeX = (Game.GAME_WIDTH - SLIDER_WIDTH) / 2 ;
        int volumeY = backgroundY + 513;
        volumeButton = new ButtonVolume(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        //Update the sound button
        musicButton.update();
        sfxButton.update();

        //Update the URM buttons
        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        //Update the volume button
        volumeButton.update();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(Graphics g){
        //Draw background
        g.drawImage(background, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        //Draw sound button
        musicButton.draw(g);
        sfxButton.draw(g);

        //Draw the URM buttons
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        //Draw the volume button
        volumeButton.draw(g);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseDragged(MouseEvent e){
        if (volumeButton.isMousePressed()){
            volumeButton.changeX(e.getX());
        }

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

        //Set the URM buttons are pressed
        if (isIn(e, menuButton)){
            menuButton.setMousePressed(true);
        }

        if (isIn(e, replayButton)){
            replayButton.setMousePressed(true);
        }

        if (isIn(e, unpauseButton)){
            unpauseButton.setMousePressed(true);
        }

        //Set the volume button is pressed
        if (isIn(e, volumeButton)){
            volumeButton.setMousePressed(true);
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

        //Reset the booleans for the sound buttons
        musicButton.resetBoolean();
        sfxButton.resetBoolean();

        //If the menu button is pressed, the game goes to the main menu
        if (isIn(e, menuButton) && menuButton.isMousePressed()) {
            Gamestate.currentState = Gamestate.MENU;
            playing.unpauseGame();
        }

        //If the replay button is pressed, the game restarts
        if (isIn(e, replayButton) && replayButton.isMousePressed()) {
            playing.restartAll();
            playing.unpauseGame();
        }

        //If the unpause button is pressed, the game continues
        if (isIn(e, unpauseButton) && unpauseButton.isMousePressed()) {
            playing.unpauseGame();
        }

        //Reset the URM buttons
        menuButton.resetBooleans();
        replayButton.resetBooleans();
        unpauseButton.resetBooleans();

        //If the volume button is pressed, the volume changes
        volumeButton.resetBooleans();

    }

    public void mouseMoved(MouseEvent e) {
        //Reset the mouse over for the sound buttons
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if(isIn(e, musicButton)){
            musicButton.setMouseOver(true);
        }

        if(isIn(e, sfxButton)){
            sfxButton.setMouseOver(true);
        }

        //Reset the mouse over for the URM buttons
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);

        if(isIn(e, menuButton)){
            menuButton.setMouseOver(true);
        }

        if(isIn(e, replayButton)){
            replayButton.setMouseOver(true);
        }

        if(isIn(e, unpauseButton)){
            unpauseButton.setMouseOver(true);
        }

        //Reset the mouse over for the volume button
        volumeButton.setMouseOver(false);

        if(isIn(e, volumeButton)){
            volumeButton.setMouseOver(true);
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check if the mouse is in the button
    private boolean isIn(MouseEvent e, ButtonPause buttonPause){
        return buttonPause.getBounds().contains(e.getX(), e.getY());
    }

}
