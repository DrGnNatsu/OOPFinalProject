package Inputs;

import Game.GamePanel;
import Gamestates.Gamestate;

import java.awt.event.*;

public class MouseInputs implements MouseListener, MouseMotionListener{
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(Gamestate.currentState){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Gamestate.currentState);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(Gamestate.currentState){
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Gamestate.currentState);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(Gamestate.currentState){
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Gamestate.currentState);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch(Gamestate.currentState){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(Gamestate.currentState){
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Gamestate.currentState);
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
