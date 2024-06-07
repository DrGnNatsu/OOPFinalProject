package Gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GUI.AudioOptions;
import GUI.ButtonURM;
import Game.Game;
import Utilization.LoadSaveFile;

import static Utilization.ConstantVariables.GUI.URMButton.URM_BUTTON_SIZE;
import static Utilization.LoadSaveFile.*;

public class Options extends State implements StateMethod{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private AudioOptions audioOptions;
    private BufferedImage optionsMenuImage;
    //Background
    private BufferedImage borderMenuBackground, insideMenuBackground;
    private BufferedImage bg1, bg2, bg3;
    //Variables for background
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    private int menuX1, menuY1, menuWidth1, menuHeight1;
    private int menuX2, menuY2, menuWidth2, menuHeight2;
    //Variables for buttons
    private ButtonURM menuButton;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Options(Game game){
        super(game);
        audioOptions = game.getAudioOptions();
        loadImages();
        loadButtons();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load buttons
    private void loadButtons() {
        int menuX = backgroundX + 230;
        int menuY = backgroundY + 615;
        menuButton = new ButtonURM(menuX, menuY, URM_BUTTON_SIZE, URM_BUTTON_SIZE, 2);

    }

    //Load images
    private void loadImages() {
        //Load the border menu background
        BufferedImage temp = LoadSaveFile.importMap(BORDER_MENU_BACKGROUND);
        borderMenuBackground = temp.getSubimage(64 * 2, 0, 64, 64);
        menuWidth1 = borderMenuBackground.getWidth() * 11;
        menuHeight1 =  borderMenuBackground.getHeight() * 15;
        menuX1 = Game.GAME_WIDTH / 2 - menuWidth1 / 2;
        menuY1 = Game.GAME_HEIGHT / 2 - menuHeight1 / 2;

        //Load the inside menu background
        temp = LoadSaveFile.importMap(INSIDE_MENU_BACKGROUND);
        insideMenuBackground = temp.getSubimage(96 * 4, 0, 96, 94);
        menuWidth2 = insideMenuBackground.getWidth() * 11 / 2;
        menuHeight2 = insideMenuBackground.getHeight() * 7;
        menuX2 = Game.GAME_WIDTH / 2 - menuWidth2 / 2;
        menuY2 = Game.GAME_HEIGHT / 2 - menuHeight2 / 2 - 50;

        //Load the background
        bg1 = LoadSaveFile.importMap(BACKGROUND1_LSF);
        bg2 = LoadSaveFile.importMap(BACKGROUND2_LSF);
        bg3 = LoadSaveFile.importMap(BACKGROUND3_LSF);

        //Menu
        optionsMenuImage = LoadSaveFile.importMap(OPTIONS_MENU);
        backgroundWidth = optionsMenuImage.getWidth() * 2;
        backgroundHeight = optionsMenuImage.getHeight() * 2;
        backgroundX = (Game.GAME_WIDTH - backgroundWidth) / 2;
        backgroundY = (Game.GAME_HEIGHT - backgroundHeight) / 2;

    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Implements method
    @Override
    public void update() {
        menuButton.update();
        audioOptions.update();

    }

    @Override
    public void draw(Graphics g) {
        //Draw screen background
        g.drawImage(bg1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(bg2, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(bg3, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        //Draw the background menu
        g.drawImage(borderMenuBackground, menuX1, menuY1, menuWidth1, menuHeight1, null);
        g.drawImage(insideMenuBackground, menuX2, menuY2, menuWidth2, menuHeight2, null);

        //Draw the options menu
        g.drawImage(optionsMenuImage, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        //Draw the buttons
        menuButton.draw(g);
        audioOptions.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()){
                Gamestate.currentState = Gamestate.MENU;
            }

        } else {
            audioOptions.mouseReleased(e);
        }

        menuButton.resetBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        if (isIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.currentState = Gamestate.MENU;
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Mouse
    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }

    //Support method
    private boolean isIn(MouseEvent e, ButtonURM button){
        return button.getBounds().contains(e.getX(), e.getY());
    }

}
