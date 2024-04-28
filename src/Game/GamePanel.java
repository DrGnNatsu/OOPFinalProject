package Game;
import Inputs.KeyBoardInputs;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        addKeyListener(new KeyBoardInputs());
        setFocusable(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(50, 50, 100, 100);
    }

}
