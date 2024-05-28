package Utilization;

import Game.Game;

public class SupportMethods {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check if the player can move
    public static boolean canMove(float x, float y, float width, float height, int[][] levelData){
        //Check if the player can move
        return !isSolid(x, y, levelData) && !isSolid(x + width, y + height, levelData) &&
                !isSolid(x + width, y, levelData) && !isSolid(x, y + height, levelData);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check Solid of the level
    public static boolean isSolid(float x, float y, int[][] levelData){
        //Two edge of the screen top and down
        if (x < 0 || x >= Game.GAME_WIDTH)  return true;
        if (y < 0 || y >= Game.GAME_HEIGHT) return true;

        float xIndex = x / Game.TILE_SIZE_SCALE;
        float yIndex = y / Game.TILE_SIZE_SCALE;

        //Check which sprite is solid or not
        int value  = levelData[(int) yIndex][(int) xIndex];

        return value != 0;
    }
}
