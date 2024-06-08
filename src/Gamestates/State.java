package Gamestates;

import Audio.AudioPlayer;
import GUI.ButtonMenu;
import Game.Game;

import java.awt.event.MouseEvent;

public class State {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    protected Game game;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public State(Game game){
        this.game = game;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Methods
    public boolean isIn(MouseEvent e, ButtonMenu buttonMenu){
        return buttonMenu.getBounds().contains(e.getX(), e.getY());
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameState (Gamestate currentState){
        switch (currentState){
            case MENU:
                game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
                break;
            case PLAYING:
                game.getAudioPlayer().playSong(game.getPlaying().getLevelManager().getLevelIndex());
                break;

        }

        Gamestate.currentState = currentState;
    }

}
