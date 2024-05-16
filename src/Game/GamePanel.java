package Game;

import Inputs.*;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialise the variables
    //Create mouse
    private final MouseInputs mouseInputs;
    //Create the game
    private final Game game;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public GamePanel(Game game) {
        setPanelSize();
        this.game = game;
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
    //Game update
    public void gameUpdate() {

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Paint
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.renderAnimation(g);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get the game
    public Game getGame() {
        return game;
    }
}


