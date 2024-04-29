package Inputs;

import Game.GamePanel;

import java.awt.event.*;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyBoardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.doChangeYAxis(-10);
                break;
            case KeyEvent.VK_S:
                gamePanel.doChangeYAxis(+10);
                break;
            case KeyEvent.VK_A:
                gamePanel.doChangeXAxis(-10);
                break;
            case KeyEvent.VK_D:
                gamePanel.doChangeXAxis(10);
                break;
            default:
                System.out.println("Invalid Key");
                break;
        }

    }

}
