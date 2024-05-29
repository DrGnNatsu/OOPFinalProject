package Inputs;

import Game.GamePanel;

import java.awt.event.*;
import static Utilization.ConstantVariables.PlayerConstant.*;

public class KeyBoardInputs implements KeyListener {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private final GamePanel gamePanel;
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public KeyBoardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(true);
                break;
            default:
                System.out.println("Invalid key");
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(false);
                break;
            default:
                System.out.println("Invalid key");
                break;
        }

        if (!gamePanel.getGame().getPlayer().isUp() &&
            !gamePanel.getGame().getPlayer().isDown() &&
            !gamePanel.getGame().getPlayer().isLeft() &&
            !gamePanel.getGame().getPlayer().isRight()) {
            gamePanel.getGame().getPlayer().setPlayerMoving(false);
        }

    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
