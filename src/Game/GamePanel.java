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
    //Create the image
    private BufferedImage image;
    private BufferedImage[] idleAnimation = new BufferedImage[6];
    private BufferedImage[][] animation = new BufferedImage[10][8];
    //Create the animation tick to change animation
    private int animationTick, animationIndex, animationSpeed = 32;


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GamePanel() {
        setPanelSize();
        loadIdleAnimation();
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
    //Changes the position
    public void doChangeXAxis(int delta) {
        xAxisDelta += delta;
    }
    public void doChangeYAxis(int delta) {
        yAxisDelta += delta;
    }
    public void setPosition(int x, int y) {
        xAxisDelta = x;
        yAxisDelta = y;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Paint
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateIdleAnimation();
        g.drawImage(idleAnimation[animationIndex], (int) xAxisDelta, (int) yAxisDelta, 56 * 2, 56 * 2, null);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Import the image
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
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the idle animation
    private void loadIdleAnimation() {
        InputStream inputStream = getClass().getResourceAsStream("/Texture/Entities/generic_char_v02/png/blue/char_blue_1.png");
        try {
            image = ImageIO.read(inputStream);
            for (int i = 0; i < 6; i++) {
                idleAnimation[i] = image.getSubimage(56 * i, 0, 56, 56);
            }
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
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update the IDLE animation
    private void updateIdleAnimation() {
        animationTick++;
        if (animationTick > animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 6) {
                animationIndex = 0;
            }
        }
    }
}


