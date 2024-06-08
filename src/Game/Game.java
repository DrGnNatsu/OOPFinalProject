package Game;

import Audio.AudioPlayer;
import GUI.AudioOptions;
import Gamestates.Gamestate;
import Gamestates.Options;
import Gamestates.Playing;
import Gamestates.Menu;

import java.awt.*;

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
    //Create the UPS and tick variables
    private final int UPS = 512;
    private int tick = 0;
    //Create game states
    private Playing playing;
    private Menu menu;
    private AudioOptions audioOptions;
    private Options options;
    private AudioPlayer audioPlayer;

    //Create tiles
    public final static int TILE_SIZE = 24;
    public final static float TILE_SCALE = 1.5f;
    public final static float PLAYER_SCALE = 3f;
    public final static int TILE_SIZE_SCALE = (int) (TILE_SIZE * TILE_SCALE);
    public final static int TILE_WIDTH = 48;
    public final static int TILE_HEIGHT = 32;
    public final static int GAME_WIDTH = TILE_WIDTH * TILE_SIZE_SCALE;
    public final static int GAME_HEIGHT = TILE_HEIGHT * TILE_SIZE_SCALE;

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Game() {
        initializeClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        start();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize the classes
    private void initializeClasses(){
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        playing = new Playing(this);
        menu = new Menu(this);
        options = new Options(this);
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
        //Switch games tate
        switch(Gamestate.currentState){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                options.update();
                break;

            default:
                System.exit(0);
                break;
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Render the animation
    public void renderAnimation(Graphics g){
        //Switch game state
        switch(Gamestate.currentState){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
                options.draw(g);
                break;
            default:
                break;
        }

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
                //System.out.println("FPS: " + frame + " | UPS: " + tick);
                tick = 0;
                frame = 0;
            }
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //When the window focus is lost
    public void windowFocusLost(){
        if (Gamestate.currentState == Gamestate.PLAYING)
            playing.getPlayer().resetDirection();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public void setPlaying(Playing playing) {
        this.playing = playing;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    public Options getOptions() {
        return options;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

}