package Utilization;

import Game.Game;

import java.awt.geom.Rectangle2D;

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

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get the entity x position next to the wall
    public static float getEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        //Get the current tile
        int currentTile = (int) (hitbox.x / Game.TILE_SIZE);
        int tileXPosition, xOffset;
        // If xSpeed < 0, No collision on the left
        if (xSpeed > 0)  {
            //Right
            tileXPosition = currentTile * Game.TILE_SIZE;
            xOffset = (int) (Game.TILE_SIZE - hitbox.width);
            return tileXPosition + xOffset - 1; //-1 because the edge of hitbox
        }
        //Left
        return currentTile * Game.TILE_SIZE;
    }
}
