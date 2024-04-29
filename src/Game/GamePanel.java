package Game;
import Inputs.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int changeXAxis, changeYAxis;
    public void doChangeXAxis(int changeValue) {
        changeXAxis += changeValue;
    }
    public void doChangeYAxis(int changeValue) {
        changeYAxis += changeValue;
    }
    public void setRectanglePosition(int x, int y) {
        changeXAxis = x;
        changeYAxis = y;
    }
    public GamePanel() {
        addKeyListener(new KeyBoardInputs(this));
        mouseInputs = new MouseInputs(this);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(50 + changeXAxis, 50 + changeYAxis, 100, 100);
        repaint();
    }

}
