package Game;

public class Game {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Variables
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread gameThread;
    //Create the FPS and frame variables
    private final int FPS = 256;
    private int frame = 0;
    private long lastCheck = System.currentTimeMillis() ;
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
    //Run the game & check FPS to repaint the game panel
    public void run(){
        double timePerTick = 1000000000.0 / FPS;
        double lastTime = System.nanoTime();
        double now;
        while (true){
            now = System.nanoTime();
            if (now - lastTime >= timePerTick){
                gamePanel.repaint();
                lastTime = now;
                frame++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frame);
                frame = 0;
            }
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}