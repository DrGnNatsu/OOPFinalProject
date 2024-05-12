package Inputs;

import Game.GamePanel;

import java.awt.event.*;
import static Utilization.ConstantVariables.PlayerConstant.*;

public class KeyBoardInputs implements KeyListener {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private GamePanel gamePanel;
    public KeyBoardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_S:
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_A:
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDirection(RIGHT);
                break;
            default:
                System.out.println("Invalid key");
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;
            default:
                System.out.println("Invalid key");
                break;
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
