package Levels;

import Game.Game;
import Utilization.LoadSaveFile;

import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage levelImage;
    public LevelManager(Game game) {
        this.game = game;
        levelImage = LoadSaveFile.GetSpriteAtlas(LoadSaveFile.LEVEL_ATLAS_BACKGROUND);
    }
}
