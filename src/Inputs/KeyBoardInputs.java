package Inputs;

import java.awt.event.*;

public class KeyBoardInputs implements KeyListener {
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("UP");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("RIGHT");
                break;
            default:
                System.out.println("Invalid Key");
                break;
        }

    }

}
