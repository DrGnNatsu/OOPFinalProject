package Game;
import Inputs.*;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialise the variables
    //Create mouse
    private MouseInputs mouseInputs;
    //Create the variables for the rectangle
    private float xAxisDelta, yAxisDelta;
    private float xDirection = 1f, yDirection = 1f; // Use to change the speed of the rectangle
    //Create the variables for the color
    private Color color = changeColor();
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GamePanel() {
        addKeyListener(new KeyBoardInputs(this));
        mouseInputs = new MouseInputs(this);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Changes the position of the rectangle
    public void doChangeXAxis(int delta) {
        xAxisDelta += delta;
    }
    public void doChangeYAxis(int delta) {
        yAxisDelta += delta;
    }
    public void setRectanglePosition(int x, int y) {
        xAxisDelta = x;
        yAxisDelta = y;
    }
    private void updateRectanglePosition() {
        //Change the direction of the rectangle
        //When the rectangle move to edge of screen, we set the direction to opposite using *= -1 & change the color
        xAxisDelta += xDirection;
        if(xAxisDelta + 100 > getWidth()  || xAxisDelta < 0) {
            xDirection *= -1;
            color = changeColor();
        }
        yAxisDelta += yDirection;
        if(yAxisDelta + 100 > getHeight()  || yAxisDelta < 0) {
            yDirection *= -1;
            color = changeColor();
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Paint the rectangle
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect((int) xAxisDelta,(int) yAxisDelta, 100, 100);
        updateRectanglePosition();
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Change color of the rectangle
    public Color changeColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return new Color(red, green, blue);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}


