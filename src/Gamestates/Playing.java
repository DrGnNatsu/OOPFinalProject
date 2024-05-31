package Gamestates;

import Entities.Crabby;
import Entities.EnemyManager;
import Entities.Player;
import GUI.PauseOverlay;
import Levels.DrawLevel;
import Levels.LevelManager;
import Game.Game;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static Utilization.ConstantVariables.Environment.*;

public class Playing extends State implements StateMethod{
    //Create the player
    private Player player;
    //Create the level
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    //Create the draw level
    private DrawLevel DrawLevel;
    //Create the pause menu
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    //Create variables for level move to left or right
    private int xLevelOffset;
    private int leftBorder = (int) (Game.GAME_WIDTH * 0.3);
    private int rightBorder = (int) (Game.GAME_WIDTH * 0.7);
    private int levelTilesWide = 130;
    private int maxTileOffset = levelTilesWide  - Game.TILE_WIDTH;
    private int maxLevelOffsetX = maxTileOffset * Game.TILE_SIZE_SCALE;
    //Create background image
    private BufferedImage background;
    private BufferedImage bigClouds, smallClouds;
    private Random random = new Random();
    int[] smallCloudPosition = new int[8];


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Constructor
    public Playing(Game game) {
        super(game);
        initializeClasses();

        background = LoadSaveFile.importMap(LoadSaveFile.PLAYING_BACKGROUND);
        bigClouds = LoadSaveFile.importMap(LoadSaveFile.BIG_CLOUDS);
        smallClouds = LoadSaveFile.importMap(LoadSaveFile.SMALL_CLOUDS);

        for (int i = 0; i < 8; i++){
            smallCloudPosition[i] = (int) (55 * Game.TILE_SCALE) * random.nextInt((int) (100 * Game.TILE_SCALE));
        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize the classes
    private void initializeClasses(){
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this, levelManager.getLevel1());
        player = new Player(100, (Game.TILE_HEIGHT - 10 ) * Game.TILE_SIZE_SCALE,
                (int) (56 * Game.PLAYER_SCALE) , (int) (56 * Game.PLAYER_SCALE));
        player.getLevelData(levelManager.getLevel1());
        DrawLevel = new DrawLevel(game);
        pauseOverlay = new PauseOverlay(this);
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Unpause the game
    public void unpauseGame(){
        paused = false;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //When the window focus is lost
    public void windowFocusLost(){
        player.resetDirection();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //MouseDragged
    public void mouseDragged(MouseEvent e) {
        if (paused) pauseOverlay.mouseDragged(e);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check if the player is close to the border
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int delta = playerX - xLevelOffset;
        //Check if the player is close to the border
        if (delta > rightBorder) xLevelOffset += delta - rightBorder;
        else if (delta < leftBorder) xLevelOffset -= leftBorder - delta;
        //Check if the level is at the edge
        if (xLevelOffset > maxLevelOffsetX) xLevelOffset = maxLevelOffsetX;
        else if (xLevelOffset < 0) xLevelOffset = 0;

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Draw the clouds
    private void drawClouds(Graphics g) {
        for(int i = 0; i < 8; i++){
            g.drawImage(bigClouds, BIG_CLOUDS_WIDTH * i - (int) (xLevelOffset * 0.3), 100 , BIG_CLOUDS_WIDTH, BIG_CLOUDS_HEIGHT, null);
        }

        for (int i = 0; i < 8; i++) {
            g.drawImage(smallClouds, SMALL_CLOUDS_WIDTH * i * 2 - (int) (xLevelOffset * 0.7), smallCloudPosition[i], SMALL_CLOUDS_WIDTH, SMALL_CLOUDS_HEIGHT, null);
        }
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Implement the methods from the StateMethod interface
    @Override
    public void update() {
        if(!paused) {
            levelManager.update();
            enemyManager.update();
            player.update();
            checkCloseToBorder();

        } else pauseOverlay.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        DrawLevel.draw(g, xLevelOffset);
        drawClouds(g);
        player.renderAnimations(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);

        if (paused){
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Left click
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttack(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) pauseOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) pauseOverlay.mouseMoved(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
            default:
                System.out.println("Invalid key");
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
            case KeyEvent.VK_BACK_SPACE:
                Gamestate.currentState = Gamestate.MENU;
                break;
            default:
                System.out.println("Invalid key");
                break;
        }

        if (!player.isUp() && !player.isDown() &&
                !player.isLeft() && !player.isRight())
            player.setPlayerMoving(false);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public Player getPlayer() {
        return player;
    }

}
