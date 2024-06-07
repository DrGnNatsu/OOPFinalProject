package GUI;

import Game.Game;
import Gamestates.Gamestate;
import Gamestates.Playing;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.URMButton.URM_BUTTON_SIZE;

public class GameOver_OverLay {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;
    //Image
    private BufferedImage image;
    private int imageX, imageY, imageWidth, imageHeight;
    //Buttons
    private ButtonURM menuButton, replayButton;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GameOver_OverLay(Playing playing){
        this.playing = playing;
        createImage();
        createButtons();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update
    public void update(){
        menuButton.update();
        replayButton.update();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create buttons
    private void createButtons(){
        int menuX = imageX + 75;
        int replayX = imageX + 285;
        int y = imageY + 205;
        menuButton = new ButtonURM(menuX, y, URM_BUTTON_SIZE, URM_BUTTON_SIZE, 2);
        replayButton = new ButtonURM(replayX, y, URM_BUTTON_SIZE, URM_BUTTON_SIZE, 1);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Create image
    private void createImage(){
        image = LoadSaveFile.importMap(LoadSaveFile.DEATH_MENU);
        imageWidth = image.getWidth() * 2;
        imageHeight = image.getHeight() * 2;
        imageX = (Game.GAME_WIDTH - imageWidth) / 2;
        imageY = (Game.GAME_HEIGHT - imageHeight) / 2 - 50;
    }

    //Draw image
    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        menuButton.draw(g);
        replayButton.draw(g);
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Arial", Font.BOLD, 50));
//        g.drawString("Game Over", Game.GAME_WIDTH / 2 - 150, Game.GAME_HEIGHT / 2 - 50);
//        g.setFont(new Font("Arial", Font.BOLD, 30));
//        g.drawString("Press ESC to restart", Game.GAME_WIDTH / 2 - 150, Game.GAME_HEIGHT / 2 + 50);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Key event
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            playing.restartAll();
            Gamestate.currentState = Gamestate.MENU;
        }

    }

    public void keyReleased(KeyEvent e){

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseMoved(MouseEvent e){
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);

        if (isIn(menuButton, e)) menuButton.setMouseOver(true);
        if (isIn(replayButton, e)) replayButton.setMouseOver(true);
    }

    public void mousePressed(MouseEvent e){
        if (isIn(menuButton, e)) menuButton.setMousePressed(true);
        if (isIn(replayButton, e)) replayButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e){
        if (menuButton.isMousePressed() && isIn(menuButton, e)){
            playing.restartAll();
            Gamestate.currentState = Gamestate.MENU;
        }

        if (replayButton.isMousePressed() && isIn(replayButton, e)){
            playing.restartAll();
        }

        menuButton.resetBooleans();
        replayButton.resetBooleans();
    }

    //Support mouse
    private boolean isIn(ButtonURM button, MouseEvent e){
        return button.getBounds().contains(e.getX(), e.getY());
    }


}
