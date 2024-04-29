package Game;

import javax.swing.*;
public class GameWindow {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public GameWindow(GamePanel gamePanel) {
        JFrame jframe = new JFrame("Game");
        jframe.setSize(800, 600);
        jframe.add(gamePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
    public GameWindow(){

    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
