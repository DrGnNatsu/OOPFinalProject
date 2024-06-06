package Utilization;

import Game.Game;
import Objects.Projectile;

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
        return isTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    //Support solid
    public static boolean isTileSolid(float xTile, float yTile, int[][] levelData) {
        int value = levelData[(int) yTile][(int) xTile];

        return value == 1;
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

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check floor
    public static boolean isFloor(Rectangle2D.Float hitbox, int[][] levelData, float xSpeed){
        //Check if the player is on the floor
        if (xSpeed > 0) return !isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        else return !isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Walkable tile
    public static boolean isAllWalkableTile(int[][] levelData, int xStart, int xEnd, int y){
        //Check if the tile is walkable
        if (y < 0 || y >= levelData.length) return false;
        if (isAllTileClear(levelData, xStart, xEnd, y))
            for(int i = 0; i < xEnd - xStart; i++){
                if(!isTileSolid(xStart + i, y, levelData)) return false;

                if(!isTileSolid(xStart + i, y + 1, levelData)) return false;

            }

        return true;

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Sight clear
    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float hitbox,
                                       Rectangle2D.Float playerHitbox, int yTile) {
        //Check if the sight is clear
        int firstXTile = (int) (hitbox.x / Game.TILE_SIZE_SCALE);
        int secondXTile = (int) ((playerHitbox.x) / Game.TILE_SIZE_SCALE);
        //Check if it is clear on the left or right
        if(firstXTile > secondXTile)
            return isAllWalkableTile(levelData, secondXTile, firstXTile, yTile);
        else
            return isAllWalkableTile(levelData, firstXTile, secondXTile, yTile);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Check cannon can see player
    public static boolean canCannonSeePlayer(int[][] levelData, Rectangle2D.Float hitbox,
                                             Rectangle2D.Float hitbox1, int tileY) {
        //Check if the cannon can see the player

        int firstXTile = (int) (hitbox.x / Game.TILE_SIZE_SCALE);
        int secondXTile = (int) ((hitbox1.x) / Game.TILE_SIZE_SCALE);
        //Check if it is clear on the left or right
        if(firstXTile > secondXTile)
            return isAllTileClear(levelData, firstXTile, secondXTile , tileY);
        else
            return isAllTileClear(levelData, secondXTile, firstXTile, tileY);

    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Is all tile clear
    public static boolean isAllTileClear(int[][] levelData, int xStart, int xEnd, int y){
        //Check if the tile is clear
        for(int i = 0; i < xEnd - xStart; i++){
            if(isTileSolid(xStart + i, y, levelData) ||
                    !isTileSolid(xStart + i, y + 1, levelData))
                return false;

        }

        return true;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    //Is projectile hit level
    public static boolean isProjectileHitLevel(Projectile projectile, int[][] levelData){
        //Check if the projectile hit the level
        return isSolid(projectile.getHitbox().x + projectile.getHitbox().width / 2,
                projectile.getHitbox().y + projectile.getHitbox().height / 2, levelData);

    }

}
