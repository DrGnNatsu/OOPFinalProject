package Game;
import Inputs.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

import static Utilization.ConstantVariables.PlayerConstant.*;

public class GamePanel extends JPanel {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialise the variables
    //Create mouse
    private MouseInputs mouseInputs;
    //Create the variables for the rectangle
    private float xAxisDelta, yAxisDelta;
    //Create the image
    private BufferedImage image;
    private BufferedImage[][] animation = new BufferedImage[11][8];
    //Create the animation tick to change animation
    private int animationTick, animationIndex;
    private final int animationSpeed = 16;
    //Define player action
    private int playerAction = IDLE;
    //Define player direction
    private int playerDirection = -1;
    private boolean playerMoving = false;


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GamePanel() {
        setPanelSize();
        //Import the image with the right file sprite.
        if (playerAction <= 10) {
            importImage("/Texture/Entities/generic_char_v02/png/blue/char_blue_1.png");
        } else {    // Load the other animations
            importImage("/Texture/Entities/generic_char_v02/png/blue/char_blue_2.png");
        }
        loadAnimation();
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
    //Sets the player direction
    public void setDirection(int direction) {
        this.playerDirection = direction;
        playerMoving = true;
    }

    public void setMoving(boolean moving) {
        this.playerMoving = moving;
    }

    public void setAnimation(){
        if (playerMoving) {
            playerAction = WALK;
        } else {
            playerAction = IDLE;
        }
    }

    public void updatePosition() {
        if (playerMoving) {
            switch (playerDirection) {
                case LEFT -> xAxisDelta -= 5;
                case RIGHT -> xAxisDelta += 5;
                case UP -> yAxisDelta -= 5;
                case DOWN -> yAxisDelta += 5;
            }
        }
    }
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
        updateAnimation();
        setAnimation();
        updatePosition();
        g.drawImage(animation[2][animationIndex], (int) xAxisDelta, (int) yAxisDelta, 56 * 2, 56 * 2, null);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Import the image
    private void importImage(String path) {
        InputStream inputStream = getClass().getResourceAsStream(path);
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
    //Load the animation
    private void loadAnimation() {
        for (int i = 0; i < animation.length; i++) {
            for (int j = 0; j < animation[i].length; j++) {
                animation[i][j] = image.getSubimage(j * 56, i * 56, 56, 56);
            }
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update the animation
    private void updateAnimation() {
        animationTick++;
        if (animationTick > animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }
}


