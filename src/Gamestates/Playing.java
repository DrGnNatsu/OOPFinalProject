package Gamestates;

import Entities.EnemyManager;
import Entities.Player;
import GUI.GameOver_OverLay;
import GUI.LevelCompleteOverlay;
import GUI.PauseOverlay;
import Levels.DrawLevel;
import Levels.LevelManager;
import Game.Game;
import Objects.ObjectManager;
import Utilization.LoadSaveFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static Utilization.ConstantVariables.Environment.*;

public class Playing extends State implements StateMethod{
    //Create the player
    private Player player;
    //Create the level
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    //Create the draw level
    private DrawLevel DrawLevel;
    //Create the pause menu
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    //Create the game over
    private GameOver_OverLay gameOverOverlay;
    private boolean gameOver = false;
    private boolean playerDeath = false;
    //Create the level completed
    private LevelCompleteOverlay levelCompleteOverlay;
    private boolean levelCompleted = false;
    //Create variables for level move to left or right
    private int xLevelOffset;
    private final int leftBorder = (int) (Game.GAME_WIDTH * 0.3);
    private final int rightBorder = (int) (Game.GAME_WIDTH * 0.7);
    private final int levelTilesWide = 130;
    private int maxTileOffset = levelTilesWide  - Game.TILE_WIDTH;
    private int maxLevelOffsetX = maxTileOffset * Game.TILE_SIZE_SCALE;
    //private int maxLevelOffsetX;
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

        calculateOffset();
        loadStartLevel();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Load the start level
    private void loadStartLevel() {
        switch (levelManager.getLevelIndex()){
            case 0:
                player.getLevelData(levelManager.getLevel1());
                enemyManager = new  EnemyManager(this, levelManager.getLevel1());
                objectManager.loadObject(levelManager.getLevel1());
                break;
            case 1:
                player.getLevelData(levelManager.getLevel2());
                enemyManager = new  EnemyManager(this, levelManager.getLevel2());
                objectManager.loadObject(levelManager.getLevel2());
                break;
        }

    }

    //load next level
    public void loadNextLevel(){
        levelCompleted = false;
        paused = false;
        gameOver = false;
        restartAll();
        levelManager.loadNextLevel();
        calculateOffset();

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Calculate the offset
    private void calculateOffset() {
        maxLevelOffsetX = Game.TILE_SIZE_SCALE * maxTileOffset;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Initialize the classes
    private void initializeClasses(){
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);
        player = new Player(100, (Game.TILE_HEIGHT - 10 ) * Game.TILE_SIZE_SCALE,
                (int) (56 * Game.PLAYER_SCALE) , (int) (56 * Game.PLAYER_SCALE), this);
        switchMethod();
        DrawLevel = new DrawLevel(game, levelManager);
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOver_OverLay(this);
        levelCompleteOverlay = new LevelCompleteOverlay(this);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Switch the level
    public void switchMethod(){
        switch (levelManager.getLevelIndex()){
            case 0:
                player.getLevelData(levelManager.getLevel1());
                enemyManager = new  EnemyManager(this, levelManager.getLevel1());
                objectManager.loadObject(levelManager.getLevel1());
                break;
            case 1:
                player.getLevelData(levelManager.getLevel2());
                enemyManager = new  EnemyManager(this, levelManager.getLevel2());
                objectManager.loadObject(levelManager.getLevel2());
                break;
        }
    }

    //Update switch
    public void switchEnemyManagerUpdate(){
        switch (levelManager.getLevelIndex()){
            case 0:
                enemyManager.update(levelManager.getLevel1(), player);
                break;
            case 1:
                enemyManager.update(levelManager.getLevel2(), player);
                break;
        }
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
    //Reset the game
    public void restartAll(){
        gameOver = false;
        paused = false;
        playerDeath = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
        initializeClasses();
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check enemy hit
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }

    //Check potion touched
    public void checkPotionTouched(Rectangle2D.Float hitbox){
        objectManager.checkPotionTouched(hitbox);
    }

    //Check object hit
    public void checkObjectHit(Rectangle2D.Float attackBox){
        objectManager.checkObjectHit(attackBox);
    }

    //Check spike hit
    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Implement the methods from the StateMethod interface
    @Override
    public void update() {
        if (paused){
            pauseOverlay.update();
            return;
        }

        if(levelCompleted){
            levelCompleteOverlay.update();
            return;
        }

        if (gameOver){
            gameOverOverlay.update();
            return;
        }

        if (playerDeath){
            player.update();
            return;
        }

        levelManager.update();
        objectManager.update(levelManager.getCurrentLevel(), player);
        switchEnemyManagerUpdate();
        player.update();
        checkCloseToBorder();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        DrawLevel.draw(g, xLevelOffset);
        drawClouds(g);
        player.renderAnimations(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);
        objectManager.draw(g, xLevelOffset);

        if (paused){
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }

        if(gameOver){
            gameOverOverlay.draw(g);
        }

        if(levelCompleted){
            levelCompleteOverlay.draw(g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Left click
        if(!gameOver && e.getButton() == MouseEvent.BUTTON1)
            player.setAttack(true);

        //Right click
        if(!gameOver && e.getButton() == MouseEvent.BUTTON3)
            player.powerAttack();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver){
            if (paused)
                pauseOverlay.mousePressed(e);
            else if (levelCompleted)
                levelCompleteOverlay.mousePressed(e);
        } else {
            gameOverOverlay.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if (levelCompleted)
                levelCompleteOverlay.mouseReleased(e);
        }
        else {
            gameOverOverlay.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver){
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (levelCompleted)
                levelCompleteOverlay.mouseMoved(e);
        }
        else {
            gameOverOverlay.mouseMoved(e);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
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
        if (gameOver)
            gameOverOverlay.keyReleased(e);
        else{
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

        }

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Getters and Setters
    public Player getPlayer() {
        return player;
    }

    //Game Over
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public void setMaxLevelOffsetX (int maxLevelOffsetX){
        this.maxLevelOffsetX = maxLevelOffsetX;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
        if (levelCompleted)
            game.getAudioPlayer().playLevelCompleted();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public boolean isPlayerDeath() {
        return playerDeath;
    }

    public void setPlayerDeath(boolean playerDeath) {
        this.playerDeath = playerDeath;
    }
}
