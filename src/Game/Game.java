package Game;

public class Game {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private final GamePanel gamePanel;
    private final GameWindow gameWindow;
    private Thread gameThread;
    //Create the FPS and frame variables
    private final int FPS = 256;
    private int frame = 0;
    private long lastCheck = System.currentTimeMillis() ;
    // Create the UPS and tick variables
    private final int UPS = 512;
    private int tick = 0;
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        start();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Start the game used the game thread
    public void start(){
        gameThread = new Thread(this::run);
        gameThread.start();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Update the game panel
    public void updateGamePanel(){
        gamePanel.gameUpdate();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Run the game & check FPS to repaint the game panel
    public void run(){
        // Define some variables to calculate the time to refresh the game panel (FPS)
        double timePerFrame = 1000000000.0 / FPS;
        double deltaFPS = 0;
        // Define some variables to calculate the time to refresh the game panel (UPS)
        double timePerTick = 1000000000.0 / UPS;
        double deltaUPS = 0;
        // Define the last time to refresh the game panel
        long lastTime = System.nanoTime();
        long currentTime;

        while (true){
            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            currentTime = System.nanoTime();
            // Calculate the time to refresh the game panel --> 512 UPS
            deltaUPS += (currentTime - lastTime) / timePerTick;
            // Calculate the time to refresh the game panel --> 256 FPS
            deltaFPS += (currentTime - lastTime) / timePerFrame;
            // Update the last time
            lastTime = currentTime;

            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // If deltaUPS >= 1, tick the game panel
            if (deltaUPS >= 1){
                updateGamePanel();
                tick++;
                deltaUPS--;
            }

            //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // If now - lastTime >= timePerFrame (speed of 1 frane need to pass to draw it new) repaint the game panel
            if (deltaFPS >= 1){
                gamePanel.repaint();
                frame++;
                deltaFPS--;
            }

            // Check the FPS and UPS
            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frame + " | UPS: " + tick);
                tick = 0;
                frame = 0;
            }
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}