package Inputs;

import Game.GamePanel;
import java.awt.event.*;

public class MouseInputs implements MouseListener, MouseMotionListener{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Left click
        if (e.getButton() == MouseEvent.BUTTON1) {
            gamePanel.getGame().getPlayer().setAttack(true);
        }
        //Right click
//        if (e.getButton() == MouseEvent.BUTTON3) {
//            gamePanel.getGame().getPlayer().setDefense(true);
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
