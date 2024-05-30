package Gamestates;

import Entities.Player;
import GUI.PauseOverlay;
import Levels.DrawLevel;
import Levels.LevelManager;
import Game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethod{
    //Create the player
    private Player player;
    //Create the level
    private LevelManager levelManager;
    //Create the draw level
    private DrawLevel DrawLevel;
    //Create the pause menu
    private PauseOverlay pauseOverlay;
    private boolean paused = true;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Playing(Game game) {
        super(game);
        initializeClasses();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize the classes
    private void initializeClasses(){
        levelManager = new LevelManager(game);
        player = new Player(100, (Game.TILE_HEIGHT - 10 ) * Game.TILE_SIZE_SCALE,
                (int) (56 * Game.PLAYER_SCALE) , (int) (56 * Game.PLAYER_SCALE));
        player.getLevelData(levelManager.getLevel1());
        DrawLevel = new DrawLevel(game);
        pauseOverlay = new PauseOverlay();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //When the window focus is lost
    public void windowFocusLost(){
        player.resetDirection();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get the player
    public Player getPlayer() {
        return player;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Implement the methods from the StateMethod interface
    @Override
    public void update() {
        levelManager.update();
        player.update();

        if (paused) pauseOverlay.update();

    }

    @Override
    public void draw(Graphics g) {
        DrawLevel.draw(g);
        player.renderAnimations(g);
        if (paused)
            pauseOverlay.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Left click
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttack(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) pauseOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) pauseOverlay.mouseMoved(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            default:
                System.out.println("Invalid key");
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
            case KeyEvent.VK_BACK_SPACE:
                Gamestate.currentState = Gamestate.MENU;
                break;
            default:
                System.out.println("Invalid key");
                break;
        }

        if (!player.isUp() && !player.isDown() &&
                !player.isLeft() && !player.isRight())
            player.setPlayerMoving(false);

    }
}
