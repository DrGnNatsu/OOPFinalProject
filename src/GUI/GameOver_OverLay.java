package GUI;

import Game.Game;
import Gamestates.Gamestate;
import Gamestates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOver_OverLay {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private Playing playing;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GameOver_OverLay(Playing playing){
        this.playing = playing;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", Game.GAME_WIDTH / 2 - 150, Game.GAME_HEIGHT / 2 - 50);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Press ESC to restart", Game.GAME_WIDTH / 2 - 150, Game.GAME_HEIGHT / 2 + 50);

    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            playing.restartAll();
            Gamestate.currentState = Gamestate.MENU;
        }

    }

    public void keyReleased(KeyEvent e){

    }

}
