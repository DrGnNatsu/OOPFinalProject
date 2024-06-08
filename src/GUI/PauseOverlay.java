package GUI;

import Game.Game;
import Gamestates.Gamestate;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.URMButton.URM_BUTTON_SIZE;

public class PauseOverlay {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables for background
    private BufferedImage background = null;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;

    //Variables for URM button
    private AudioOptions audioOptions;
    private ButtonURM menuButton, replayButton, unpauseButton;

    //Variables for the playing game state
    private Playing playing;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public PauseOverlay(Playing playing){
        this.playing = playing;
        loadBackground();
        createURMButton();
        audioOptions = playing.getGame().getAudioOptions();
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
    //Create URM button
    private void createURMButton() {
        //Supporting variables
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


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        //Update the URM buttons
        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        //Update the audio options
        audioOptions.update();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(Graphics g){
        //Draw background
        g.drawImage(background, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        //Draw the URM buttons
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        //Draw the audio options
        audioOptions.draw(g);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
        //Set the audio options are pressed
        audioOptions.mousePressed(e);

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

    }

    public void mouseReleased(MouseEvent e) {
        //Audio Options
        audioOptions.mouseReleased(e);

        //If the menu button is pressed, the game goes to the main menu
        if (isIn(e, menuButton) && menuButton.isMousePressed()) {
            playing.restartAll();
            playing.setGameState(Gamestate.MENU);
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

    }

    public void mouseMoved(MouseEvent e) {
        //Audio Options
        audioOptions.mouseMoved(e);

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

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check if the mouse is in the button
    private boolean isIn(MouseEvent e, ButtonPause buttonPause){
        return buttonPause.getBounds().contains(e.getX(), e.getY());
    }

}
