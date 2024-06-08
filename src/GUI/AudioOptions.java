package GUI;

import Game.Game;
import Gamestates.Gamestate;
import Utilization.LoadSaveFile;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.PauseButton.SOUND_SIZE;
import static Utilization.ConstantVariables.GUI.VolumeButton.SLIDER_WIDTH;
import static Utilization.ConstantVariables.GUI.VolumeButton.VOLUME_HEIGHT;

public class AudioOptions {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Game game;
    //Variables for volume button
    private ButtonVolume volumeButton;

    //Variables for sound button
    private ButtonSound musicButton, sfxButton;

    //Variables for background
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    private BufferedImage background;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public AudioOptions(Game game) {
        this.game = game;
        loadPosition();
        createVolumeButton();
        createSoundButton();
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

    //Create volume button
    private void createVolumeButton(){
        int volumeX = (Game.GAME_WIDTH - SLIDER_WIDTH) / 2 ;
        int volumeY = backgroundY + 513;
        volumeButton = new ButtonVolume(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load position
    private void loadPosition(){
        background = LoadSaveFile.importMap(LoadSaveFile.PAUSE_MENU);
        backgroundWidth = background.getWidth() * 2;
        backgroundHeight = background.getHeight() * 2;
        backgroundX = (Game.GAME_WIDTH - backgroundWidth) / 2;
        backgroundY = (Game.GAME_HEIGHT - backgroundHeight - 50) / 2;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        volumeButton.update();
        musicButton.update();
        sfxButton.update();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(java.awt.Graphics g){
        volumeButton.draw(g);
        musicButton.draw(g);
        sfxButton.draw(g);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseDragged(MouseEvent e){
        if (volumeButton.isMousePressed()){
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();

            if (valueBefore != valueAfter){
                game.getAudioPlayer().setVolume(valueAfter);
            }

        }

    }

    public void mousePressed(MouseEvent e) {
        //Set the music button is pressed
        if (isIn(e, musicButton)){
            musicButton.setMousePressed(true);
        }

        //Set the volume button is pressed
        if (isIn(e, volumeButton)){
            volumeButton.setMousePressed(true);
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
            game.getAudioPlayer().toggleSongMute();
        }

        //If the sfxButton was muted, it becomes unmuted and vice versa
        if (isIn(e, sfxButton) && sfxButton.isMousePressed()){
            sfxButton.setMuted(!sfxButton.isMuted());
            game.getAudioPlayer().toggleEffectMute();
        }

        //Reset the booleans for the sound buttons
        musicButton.resetBoolean();
        sfxButton.resetBoolean();

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
