package Game;
import Inputs.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
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
    //
    private BufferedImage image, subImage;
    private BufferedImage[] idleAnimation = new BufferedImage[6];

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GamePanel() {
        setPanelSize();
        importImage();
        addKeyListener(new KeyBoardInputs(this));
        mouseInputs = new MouseInputs(this);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);

    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Set the size of the panel
    private void setPanelSize() {
        Dimension dimension = new Dimension(1200, 720);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
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
//        g.setColor(color);
//        g.fillRect((int) xAxisDelta,(int) yAxisDelta, 100, 100);
        subImage = image.getSubimage(56 * 4,56 * 2,56, 56);
        g.drawImage(subImage, (int) xAxisDelta, (int) yAxisDelta, 56 * 2, 56 * 2, null);
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
    private void importImage() {
        InputStream inputStream = getClass().getResourceAsStream("/Texture/Entities/generic_char_v02/png/blue/char_blue_1.png");
        try {
            image = ImageIO.read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


