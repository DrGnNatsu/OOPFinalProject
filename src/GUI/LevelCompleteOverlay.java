package GUI;

import Game.Game;
import Gamestates.Gamestate;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.URMButton.URM_BUTTON_SIZE;
import static Utilization.LoadSaveFile.importMap;

public class LevelCompleteOverlay {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;
    //Buttons
    private ButtonURM menuButton, nextButton;
    //Images
    private BufferedImage image = null;
    //Position
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public LevelCompleteOverlay(Playing playing){
        this.playing = playing;
        initializeImage();
        initializeButtons();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize images
    private void initializeImage(){
        image = importMap(LoadSaveFile.LEVEL_COMPLETED_MENU);
        backgroundWidth = image.getWidth() * 5 / 2;
        backgroundHeight = image.getHeight() * 5 / 2;
        backgroundX = (Game.GAME_WIDTH - backgroundWidth) / 2;
        backgroundY = (Game.GAME_HEIGHT - backgroundHeight) / 2;
    }

    //Initialize buttons
    private void initializeButtons(){
        int nextX = backgroundX + 90;
        int menuX = backgroundX + 360;
        int y = backgroundY + 325;
        menuButton = new ButtonURM(nextX, y, URM_BUTTON_SIZE * 3 / 2, URM_BUTTON_SIZE * 3 / 2, 2);
        nextButton = new ButtonURM(menuX, y, URM_BUTTON_SIZE * 3 / 2, URM_BUTTON_SIZE * 3 / 2, 0);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw
    public void draw(Graphics g){
        g.drawImage(image, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);
        menuButton.draw(g);
        nextButton.draw(g);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        menuButton.update();
        nextButton.update();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseMoved(MouseEvent e){
        menuButton.setMouseOver(false);
        nextButton.setMouseOver(false);
        if (isIn(menuButton, e)) menuButton.setMouseOver(true);
        if (isIn(nextButton, e)) nextButton.setMouseOver(true);

    }

    public void mousePressed(MouseEvent e){
        if (isIn(menuButton, e)) menuButton.setMousePressed(true);
        if (isIn(nextButton, e)) nextButton.setMousePressed(true);

    }

    public void mouseReleased(MouseEvent e){
        if (menuButton.isMousePressed() && isIn(menuButton, e)){
            playing.restartAll();
            playing.setGameState(Gamestate.MENU);

        }

        if (nextButton.isMousePressed() && isIn(nextButton, e)){
            playing.restartAll();
            playing.loadNextLevel();
            playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
        }

        menuButton.resetBooleans();
        nextButton.resetBooleans();
    }

    //Support mouse
    private boolean isIn(ButtonURM button, MouseEvent e){
        return button.getBounds().contains(e.getX(), e.getY());
    }

}
