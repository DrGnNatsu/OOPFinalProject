package Utilization;

import Game.Game;

import java.awt.geom.Rectangle2D;

public class SupportMethods {
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check if the player can move
    public static boolean canMove(float x, float y, float width, float height, int[][] levelData) {
        //Check if the player can move
        return !isSolid(x, y, levelData) && !isSolid(x + width, y + height, levelData) &&
                !isSolid(x + width, y, levelData) && !isSolid(x, y + height, levelData);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check Solid of the level
    public static boolean isSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length;
        //Two edge of the screen top and down
        if (x < 0 || x >= maxWidth * Game.TILE_SIZE_SCALE) return true;
        if (y < 0 || y >= Game.GAME_HEIGHT) return true;

        float xIndex = x / Game.TILE_SIZE_SCALE;
        float yIndex = y / Game.TILE_SIZE_SCALE;

        //Check which sprite is solid or not
        int value = levelData[(int) yIndex][(int) xIndex];


        return value != 0;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get the entity x position next to the wall
    public static float getEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        //Get the current tile
        int currentTile = (int) (hitbox.x / (Game.TILE_SIZE * Game.PLAYER_SCALE));
        int tileXPosition, xOffset;
        // If xSpeed < 0, No collision on the left
        if (xSpeed > 0) {
            //Right
            tileXPosition = (int) (currentTile * Game.TILE_SIZE * Game.PLAYER_SCALE);
            xOffset = (int) (Game.TILE_SIZE * Game.PLAYER_SCALE - hitbox.width);
            return tileXPosition + xOffset - 1; //-1 because the edge of hitbox
        }
        //Left
        return currentTile * Game.TILE_SIZE * Game.PLAYER_SCALE;

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Get the entity y position Under the roof or above floor
    public static float getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
        //Get the current tile
        int currentTile = (int) (hitbox.y / (Game.TILE_SIZE * Game.PLAYER_SCALE));
        int tileYPosition, yOffset;
        // If ySpeed < 0, No collision
        if (airSpeed > 0) {
            //Down - Falling - touching the floor
            tileYPosition = (int) (currentTile * Game.TILE_SIZE * Game.PLAYER_SCALE);
            yOffset = (int) (Game.TILE_SIZE * Game.PLAYER_SCALE - hitbox.height);
            return tileYPosition + yOffset - 1 + Game.TILE_SIZE_SCALE; //-1 because the edge of hitbox - bug.
        }
        //Up - Jumping
        return currentTile * Game.TILE_SIZE * Game.PLAYER_SCALE;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check entities on floor
    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData){
        //Check if the player is on the floor
        return !isSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData) &&
                !isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData);
    }

}
