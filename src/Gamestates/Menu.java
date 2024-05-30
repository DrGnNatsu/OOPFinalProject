package Gamestates;

import GUI.ButtonMenu;
import Game.Game;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilization.ConstantVariables.GUI.Buttons.*;
import static Utilization.LoadSaveFile.*;

public class Menu extends State implements StateMethod {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    //Buttons
    private ButtonMenu[] button = new ButtonMenu[3];
    //Menu
    private BufferedImage borderMenuBackground, insideMenuBackground;
    private int menuX1, menuY1, menuWidth1, menuHeight1;
    private int menuX2, menuY2, menuWidth2, menuHeight2;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Menu(Game game) {
        super(game);
        loadButton();
        loadMenuBackground();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the buttons
    private void loadButton() {
        //load the buttons and set position
        button[0] = new ButtonMenu(Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2, Game.GAME_HEIGHT / 2 - BUTTON_HEIGHT * 2, 0, Gamestate.PLAYING);
        button[1] = new ButtonMenu(Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2, Game.GAME_HEIGHT / 2 - BUTTON_HEIGHT / 2, 1, Gamestate.QUIT);
        button[2] = new ButtonMenu(Game.GAME_WIDTH / 2 - BUTTON_WIDTH / 2, Game.GAME_HEIGHT / 2 + BUTTON_HEIGHT, 2, Gamestate.OPTIONS);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the menu background
    private void loadMenuBackground() {
        //Load the border menu background
        BufferedImage temp = LoadSaveFile.importMap(BORDER_MENU_BACKGROUND);
        borderMenuBackground = temp.getSubimage(64 * 2, 0, 64, 64);
        menuWidth1 = (int) (borderMenuBackground.getWidth() * 9);
        menuHeight1 = (int) (borderMenuBackground.getHeight() * 11);
        menuX1 = Game.GAME_WIDTH / 2 - menuWidth1 / 2;
        menuY1 = Game.GAME_HEIGHT / 2 - menuHeight1 / 2;

        //Load the inside menu background
        temp = LoadSaveFile.importMap(INSIDE_MENU_BACKGROUND);
        insideMenuBackground = temp.getSubimage(96 * 4, 0, 96, 94);
        menuWidth2 = (int) (insideMenuBackground.getWidth() * 4);
        menuHeight2 = (int) (insideMenuBackground.getHeight() * 5);
        menuX2 = Game.GAME_WIDTH / 2 - menuWidth2 / 2;
        menuY2 = Game.GAME_HEIGHT / 2 - menuHeight2 / 2;

    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Reset the button
    private void resetButton() {
        for (ButtonMenu buttonMenu : button) {
            buttonMenu.resetBooleanValues();
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Implement the methods from the StateMethod interface
    @Override
    public void update() {
        for (ButtonMenu bm : button) {
            bm.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        //Draw the background
        g.drawImage(borderMenuBackground, menuX1, menuY1, menuWidth1, menuHeight1, null);
        g.drawImage(insideMenuBackground, menuX2, menuY2, menuWidth2, menuHeight2, null);

        //Draw buttons
        for (ButtonMenu bm : button) {
            bm.draw(g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (ButtonMenu buttonMenu : button) {
            if(isIn(e, buttonMenu))
                buttonMenu.setMousePressed(true);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (ButtonMenu buttonMenu : button) {
            if(isIn(e, buttonMenu)){
                if(buttonMenu.isMousePressed()){
                    buttonMenu.applyGameStates();
                }
                break;
            }
        }
        resetButton();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (ButtonMenu buttonMenu : button) {
            if(isIn(e, buttonMenu))
                buttonMenu.setMouseOver(true);
            else
                buttonMenu.setMouseOver(false);

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            Gamestate.currentState = Gamestate.PLAYING;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
