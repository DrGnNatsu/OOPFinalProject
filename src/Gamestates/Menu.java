package Gamestates;

import GUI.ButtonMenu;
import Game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static Utilization.ConstantVariables.GUI.Buttons.*;

public class Menu extends State implements StateMethod {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private ButtonMenu[] button = new ButtonMenu[3];

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Menu(Game game) {
        super(game);
        loadButton();
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
